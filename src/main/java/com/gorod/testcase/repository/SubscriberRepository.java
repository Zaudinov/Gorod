package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    Set<SubscriberView> getByServicesContains(Service service);
    SubscriberView getSubscriberById(Long id);
    Set<Subscriber> findByServicesContains(Service s);
    @Query(value = "SELECT s FROM Subscriber s")
    Page<SubscriberView> getAll(Pageable pageable);
}
