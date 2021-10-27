package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Service;
import com.gorod.testcase.exception.CannotDeleteServiceException;
import com.gorod.testcase.exception.ServiceNotExistsException;
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
    public ResponseEntity<Page<SubscriberView>> getSubscriberByServiceId(
            @PathVariable("id") int id,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Service service = serviceService.findServiceById(id);

        Page<SubscriberView> subscribers = subscriberRepository.getByServicesContains(service, pageable);

        return ResponseEntity.ok(subscribers);

    }

    @GetMapping("/user/all/{id}")
    public Page<SubscriberView> getSubscriberByServiceIdWithChildren(
            @PathVariable("id") int id,
            @PageableDefault(size = 20) Pageable pageable
    ){
        Service service = serviceService.findServiceById(id);

        return serviceService.getSubscriberByServiceIdWithChildren(id, pageable);

    }

    @GetMapping
    public Iterable<Service> getHierarchy(){
        Iterable<Service> all = serviceService.getHierarchy();

        return all;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceWithoutChildren> getServiceById(@PathVariable("id") int id){
        ServiceWithoutChildren foundService = serviceRepository.getServiceById(id);

        if (foundService == null){
            throw new ServiceNotExistsException("There is no service with the id");
        }

        return ResponseEntity.ok(foundService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteServiceById(
            @PathVariable("id") int id, @RequestParam(name = "force",defaultValue = "false") boolean force,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Service foundService = serviceService.findServiceById(id);

        if(!force) {
            Page<SubscriberView> subscribers = getSubscriberByServiceId(id, pageable).getBody();
            if (!foundService.getChildren().isEmpty() || !subscribers.isEmpty()) {
                throw new CannotDeleteServiceException
                        ("Can't delete service if it has subscribers" +
                                " or its child service does");
            }
        }
        else{
            serviceService.deleteService(id);
        }
        return ResponseEntity.ok().build();
    }

}
