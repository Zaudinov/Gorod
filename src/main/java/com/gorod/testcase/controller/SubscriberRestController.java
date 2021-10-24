package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.SubscriberRepository;
import com.gorod.testcase.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/subscriber")
public class SubscriberRestController {

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping
    public List<Subscriber> getAllSubscribers(){
        return subscriberService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable("id") Long id){
        Subscriber foundSubscriber;
        try{
            foundSubscriber = subscriberService.read(id);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSubscriber);
    }


    @PostMapping
    public ResponseEntity<Subscriber> create(@RequestBody Subscriber subscriber) throws URISyntaxException {
        Subscriber createdSubscriber = subscriberService.create(subscriber);

        if(createdSubscriber == null){
            return ResponseEntity.notFound().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSubscriber.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdSubscriber);
    }

}
