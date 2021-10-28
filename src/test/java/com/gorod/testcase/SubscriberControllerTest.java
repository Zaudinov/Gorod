package com.gorod.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/add_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class SubscriberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getSubscriberTest() throws Exception{
        mockMvc.perform(get("/subscriber"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath(".content[0].account").value("1000001"))
                .andExpect(jsonPath(".content[1].account").value("1000000"));
    }

    @Test
    public void getSubscriberFilterTest() throws Exception{
        mockMvc.perform(get("/subscriber").param("filter", "1000001"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subscriber/filter/account/1000001"));
    }

    @Test
    public void getSubscriberByAccountFilterTest() throws Exception{
        mockMvc.perform(get("/subscriber/filter/account/100000_"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath(".content[0].account").value("1000001"))
                .andExpect(jsonPath(".content[1].account").value("1000000"));
    }

    @Test
    public void getSubscriberByAccountTest() throws Exception{
        mockMvc.perform(get("/subscriber/filter/account/1000001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath(".account").value("1000001"));
    }

    @Test
    public void getSubscriberByIdTest() throws Exception{
        mockMvc.perform(get("/subscriber/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("account").value("1000000"));
    }
    @Test
    public void createNewSubscriberWithoutServicesTest() throws Exception{

        Subscriber expectedSubscriber = new Subscriber();
        expectedSubscriber.setAccount("1000002");
        expectedSubscriber.setFio("Печкин Владимир Дмитриевич");

        String subscriberJson = new ObjectMapper().writeValueAsString(expectedSubscriber);
        System.out.println(subscriberJson);


        mockMvc.perform(post("/subscriber").contentType(MediaType.APPLICATION_JSON_VALUE).content(subscriberJson))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json("3"));
    }

    @Test
    public void createNewSubscriberWithValidServicesTest() throws Exception{

        Subscriber expectedSubscriber = getTestSubscriber("Жилищно-коммунальные услуги", "Горячяя Вода");


        String subscriberJson = new ObjectMapper().writeValueAsString(expectedSubscriber);
        System.out.println(subscriberJson);


        mockMvc.perform(post("/subscriber").contentType(MediaType.APPLICATION_JSON_VALUE).content(subscriberJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("3"));
    }

    @Test
    public void createNewSubscriberWithInvalidServicesTest() throws Exception{

        Subscriber expectedSubscriber = getTestSubscriber("Какой-то сервис", "Заведомо ложная информация");


        String subscriberJson = new ObjectMapper().writeValueAsString(expectedSubscriber);
        System.out.println(subscriberJson);


        mockMvc.perform(post("/subscriber").contentType(MediaType.APPLICATION_JSON_VALUE).content(subscriberJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("message").value("invalid service provided"));
    }

    @Test
    public void createNewSubscriberWithInvalidIdTest() throws Exception{

        Subscriber expectedSubscriber = getTestSubscriber("Жилищно-коммунальные услуги", "Горячяя Вода");
        expectedSubscriber.setId(1L);


        String subscriberJson = new ObjectMapper().writeValueAsString(expectedSubscriber);
        System.out.println(subscriberJson);


        mockMvc.perform(post("/subscriber").contentType(MediaType.APPLICATION_JSON_VALUE).content(subscriberJson))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("message").value("There is an existing subscriber with provided id"));
    }

    public Subscriber getTestSubscriber(String s, String s2) {
        Service service1 = new Service(1, s);
        Service service2 = new Service(4, s2);
        Set<Service> serviceSet = new HashSet<>();
        serviceSet.add(service1);
        serviceSet.add(service2);


        Subscriber expectedSubscriber = new Subscriber();
        expectedSubscriber.setAccount("1000003");
        expectedSubscriber.setFio("Печкин Дмитрий Владимирович");
        expectedSubscriber.setServices(serviceSet);
        return expectedSubscriber;
    }

}
