package com.gorod.testcase;

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
    public void createNewSubscriber() throws Exception{
        String subscriberJson = "{\"account\":\"1000002\", " +
                "\"fio\":\"Печкин Владимир Дмитриевич\"}";


//        Subscriber expectedSubscriber = new Subscriber();
//        expectedSubscriber.setId(3L);
//        expectedSubscriber.setAccount("1000002");
//        expectedSubscriber.setFio("Печкин Владимир Дмитриевич");


        mockMvc.perform(post("/subscriber").contentType(MediaType.APPLICATION_JSON_VALUE).content(subscriberJson))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.account").value("1000002"))
        .andExpect(jsonPath("$.fio").value("Печкин Владимир Дмитриевич"));
    }
}
