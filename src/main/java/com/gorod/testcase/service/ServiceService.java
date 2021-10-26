package com.gorod.testcase.service;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public Page<SubscriberView> getSubscriberByServiceIdWithChildren (int id, Pageable pageable){
        Deque<com.gorod.testcase.domain.Service> servicesToRetrieveChildren = new LinkedList<>();
        Set<Integer> servicesId = new HashSet<>();
        List<Long> subscribers = new ArrayList<>();

        servicesToRetrieveChildren.add(serviceRepository.findById(id).get());

        while(!servicesToRetrieveChildren.isEmpty()){
            com.gorod.testcase.domain.Service s = servicesToRetrieveChildren.poll();
            servicesId.add(s.getId());
            servicesToRetrieveChildren.addAll(s.getChildren());
        }

        subscribers.addAll(serviceRepository.getSubscribersIds(servicesId));

        return subscriberRepository.getByIdIn(subscribers, pageable);
    }
}
