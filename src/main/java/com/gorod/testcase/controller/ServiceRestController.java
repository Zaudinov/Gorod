package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/service")
public class ServiceRestController {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @GetMapping("/user/{id}")
    public Set<Subscriber> getSubscriberByServiceId(@PathVariable("id") int id){
        return subscriberRepository.findByServicesContains(serviceRepository.findById(id).get());

    }
    @GetMapping
    public Iterable<Service> getHierarchy(){
        Iterable<Service> all = serviceRepository.findByParent(null);

        return all;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable("id") int id){
        Service foundService;
        try{
            foundService = serviceRepository.findById(id).get();
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
