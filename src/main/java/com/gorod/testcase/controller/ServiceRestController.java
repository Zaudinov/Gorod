package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.ServiceWithoutChildren;
import com.gorod.testcase.repository.projections.SubscriberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
public class ServiceRestController {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @GetMapping("/user/{id}")
    public Page<SubscriberView> getSubscriberByServiceId(
            @PathVariable("id") int id,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){

        return subscriberRepository.getByServicesContains(serviceRepository.findById(id).get(), pageable);

    }

    @GetMapping("/user/all/{id}")
    public Set<SubscriberView> getSubscriberByServiceIdWithChildren(@PathVariable("id") int id){
        Deque<Service> servicesToRetrieveChildren = new LinkedList<>();
        Set<Service> services = new HashSet<>();
        Set<Subscriber> result = new HashSet<>();

        servicesToRetrieveChildren.add(serviceRepository.findById(id).get());

        while(!servicesToRetrieveChildren.isEmpty()){
            Service s = servicesToRetrieveChildren.poll();
            services.add(s);
            servicesToRetrieveChildren.addAll(s.getChildren());
        }
        for (Service s: services
             ) {
            result.addAll(subscriberRepository.findByServicesContains(s));
        }
        Set<SubscriberView> result2 = new HashSet<>();
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();
        for (Subscriber sub: result
             ) {
            result2.add(pf.createProjection(SubscriberView.class, sub));
        }

        return result2;

    }

    @GetMapping
    public Iterable<Service> getHierarchy(){
        Iterable<Service> all = serviceRepository.findByParent(null);

        return all;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceWithoutChildren> getServiceById(@PathVariable("id") int id){
        ServiceWithoutChildren foundService;
        try{
            foundService = serviceRepository.getServiceById(id);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteServiceById(
            @PathVariable("id") int id, @RequestParam(name = "force",defaultValue = "false") boolean force,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Service foundService;
        try {
            foundService = serviceRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if(!force) {
            if (!foundService.getChildren().isEmpty() || !getSubscriberByServiceId(id, pageable).isEmpty()) {
                return ResponseEntity.status(409).build();
            }
        }
        else{
            List<Integer> idsToBeDeleted = new ArrayList<>();
            idsToBeDeleted.add(foundService.getId());
            for (Service child: foundService.getChildren()) {
                idsToBeDeleted.add(child.getId());
            }
            serviceRepository.deleteAllById(idsToBeDeleted);
        }
        return ResponseEntity.ok().build();
    }

}
