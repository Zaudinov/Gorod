package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.repository.projections.ServiceWithoutChildren;
import com.gorod.testcase.repository.projections.SubscriberView;
import com.gorod.testcase.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/service")
public class ServiceRestController {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @GetMapping("/user/{id}")
    public Set<SubscriberView> getSubscriberByServiceId(@PathVariable("id") int id){

        return subscriberRepository.getByServicesContains(serviceRepository.findById(id).get());

    }

    @GetMapping("/user/all/{id}")
    public Set<SubscriberView> getSubscriberByServiceIdWithChildren(@PathVariable("id") int id){

        Service service = serviceRepository.findById(id).get();
        Iterable<Service> children = serviceRepository.findByParent(service.getId());
        Set<Integer> childrenId = serviceRepository.findByParent(service.getId()).stream().map(service1 -> service.getId()).collect(Collectors.toSet());
        Set<SubscriberView> result = new HashSet<>();

        for (Integer childId:
             childrenId) {
            result.addAll(getSubscriberByServiceId(childId));
        }

        return result;

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
    public ResponseEntity<Service> deleteServiceById(@PathVariable("id") int id, @RequestParam(name = "force",defaultValue = "false") boolean force) {
        Service foundService;
        try {
            foundService = serviceRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        if(!force) {
            if (!foundService.getChildren().isEmpty() || !getSubscriberByServiceId(id).isEmpty()) {
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
