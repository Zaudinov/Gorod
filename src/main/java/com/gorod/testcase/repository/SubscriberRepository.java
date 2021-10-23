package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Subscriber;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
}
