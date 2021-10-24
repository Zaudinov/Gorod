package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    Set<Service> findByParent(Integer parent);
}
