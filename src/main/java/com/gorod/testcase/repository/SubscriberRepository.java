package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    Set<Subscriber> findByServicesContains(Service service);
}
