package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

    SubscriberView getByAccount(String filter);

    Page<SubscriberView> getByServicesContains(Service service, Pageable pageable);

    SubscriberView getSubscriberById(Long id);

    @Query(value = "SELECT s FROM Subscriber s")
    Page<SubscriberView> getAll(Pageable pageable);

    @Query(value = "SELECT DISTINCT s FROM Subscriber s INNER JOIN s.services as serv WHERE serv IN (:services)")
    Page<SubscriberView> getSubscribers(@Param("services")Set<Service> s, Pageable pageable);



}
