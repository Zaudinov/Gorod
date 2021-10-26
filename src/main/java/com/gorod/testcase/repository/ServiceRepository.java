package com.gorod.testcase.repository;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.repository.projections.ServiceWithoutChildren;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    Set<Service> findByParent(Integer parent);

    ServiceWithoutChildren getServiceById(int id);

    @Query(value = "SELECT DISTINCT subscriber_id FROM subscriber_service ss WHERE ss.service_id IN :services", nativeQuery = true)
    List<Long> getSubscribersIds(@Param("services")Set<Integer> services);


}
