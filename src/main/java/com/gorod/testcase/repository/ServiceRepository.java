package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
}
