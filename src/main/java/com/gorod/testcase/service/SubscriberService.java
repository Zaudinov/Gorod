package com.gorod.testcase.service;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubscriberService {

    @Autowired
    SubscriberRepository subscriberRepository;

    public List<Subscriber> getAll(){
        return (List)subscriberRepository.getAllById();
    }

    public SubscriberView getSubscriberById(Long id){
        return subscriberRepository.getSubscriberById(id);
    }

    public Subscriber read(Long id){
        return subscriberRepository.findById(id).get();
    }

    public Subscriber create(Subscriber subscriber){
        return subscriberRepository.save(subscriber);
    }
}
