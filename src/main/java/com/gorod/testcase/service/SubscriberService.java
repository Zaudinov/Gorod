package com.gorod.testcase.service;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.exception.ServiceNotExistsException;
import com.gorod.testcase.exception.SubscriberAlreadyExistsException;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriberService {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;


    public Page<SubscriberView> getAll(Pageable pageable){
        return subscriberRepository.getAll(pageable);
    }

    public Page<SubscriberView> getByAccount(String filter, Pageable pageable){
        return subscriberRepository.getByAccountLike(filter, pageable);
    }

    public SubscriberView getSubscriberById(Long id){
        return subscriberRepository.getSubscriberById(id);
    }


    @Transactional
    public Long create(Subscriber subscriber){
        Long CreatedSubscriberId = subscriber.getId();

        //Check if there is existing subscriber with provided id
        SubscriberView subscriberInDb = null;
        if(CreatedSubscriberId != null){
            subscriberInDb = getSubscriberById(CreatedSubscriberId);
        }
        if(subscriberInDb != null){
            throw new SubscriberAlreadyExistsException("There is an existing subscriber with provided id");
        }

        //Check that provided services contain valid services, substitute set with services from DB
        Set<com.gorod.testcase.domain.Service> createdSubscriberServices = subscriber.getServices();
        Set<com.gorod.testcase.domain.Service> servicesToInject = new HashSet();
        if(createdSubscriberServices != null){

            for (com.gorod.testcase.domain.Service providedService: createdSubscriberServices) {
                int id = providedService.getId();
                com.gorod.testcase.domain.Service serviceFromDb =
                        serviceRepository.findById(id).orElseThrow(() -> new ServiceNotExistsException("invalid service provided"));

                if(!providedService.equals(serviceFromDb)){
                    throw new ServiceNotExistsException("invalid service provided");
                }
                servicesToInject.add(serviceFromDb);
            }

            subscriber.setServices(servicesToInject);
        }

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        return savedSubscriber.getId();
    }


}
