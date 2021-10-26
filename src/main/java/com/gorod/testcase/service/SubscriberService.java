package com.gorod.testcase.service;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {

    @Autowired
    SubscriberRepository subscriberRepository;


    public Page<SubscriberView> getAll(Pageable pageable){
        return subscriberRepository.getAll(pageable);
    }

    public SubscriberView getByAccount(String filter){
        return subscriberRepository.getByAccount(filter);
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
