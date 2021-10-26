package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.ServiceWithoutChildren;
import com.gorod.testcase.repository.projections.SubscriberView;
import com.gorod.testcase.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/service")
public class ServiceRestController {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceService serviceService;

    @GetMapping("/user/{id}")
    public Page<SubscriberView> getSubscriberByServiceId(
            @PathVariable("id") int id,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Service service;
        try{
            service = serviceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no service with such id");
        }

        return subscriberRepository.getByServicesContains(service, pageable);

    }

    @GetMapping("/user/all/{id}")
    public Page<SubscriberView> getSubscriberByServiceIdWithChildren(
            @PathVariable("id") int id,
            @PageableDefault(size = 20) Pageable pageable
    ){
        return serviceService.getSubscriberByServiceIdWithChildren(id, pageable);

    }

    @GetMapping
    public Iterable<Service> getHierarchy(){
        Iterable<Service> all = serviceService.getHierarchy();

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
            serviceService.deleteService(id);
        }
        return ResponseEntity.ok().build();
    }

}
