package com.gorod.testcase.service;

import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class ServiceService {
    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public Page<SubscriberView> getSubscriberByServiceIdWithChildren (int id, Pageable pageable){
        Set<com.gorod.testcase.domain.Service> services = getServiceWithChildrenDeepSet(id);


        return subscriberRepository.getSubscribers(services, pageable);

    }

    public Set<com.gorod.testcase.domain.Service> getServiceWithChildrenDeepSet(int id) {
        Deque<com.gorod.testcase.domain.Service> servicesToRetrieveChildren = new LinkedList<>();
        Set<com.gorod.testcase.domain.Service> services = new HashSet<>();

        servicesToRetrieveChildren.add(serviceRepository.findById(id).get());

        while(!servicesToRetrieveChildren.isEmpty()){
            com.gorod.testcase.domain.Service s = servicesToRetrieveChildren.poll();
            services.add(s);
            servicesToRetrieveChildren.addAll(s.getChildren());
        }
        return services;
    }
}
