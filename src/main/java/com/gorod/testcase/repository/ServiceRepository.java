package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.repository.projections.ServiceWithoutChildren;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    Set<Service> findByParent(Integer parent);

    ServiceWithoutChildren getServiceById(int id);



}
