package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    Set<Subscriber> findByServicesContains(Service service);
    SubscriberView getSubscriberById(Long id);
    @Query(value = "SELECT s FROM Subscriber s")
    List<SubscriberView> getAll();
}
