package com.gorod.testcase.controller;

import com.gorod.testcase.domain.Subscriber;
import com.gorod.testcase.exception.SubscriberNotExistsException;
import com.gorod.testcase.repository.ServiceRepository;
import com.gorod.testcase.repository.projections.SubscriberView;
import com.gorod.testcase.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/subscriber")
public class SubscriberRestController {

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping
    public ResponseEntity<Page<SubscriberView>> getAllSubscribers(
            @PageableDefault(size = 20, sort = {"account"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter
    ){
        if(filter != null){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/subscriber/filter/account/" + filter);
            return new ResponseEntity(headers, HttpStatus.FOUND) ;
        }
        return ResponseEntity.ok(subscriberService.getAll(pageable));
    }

    @GetMapping("filter/account/{account}")
    public ResponseEntity<Page<SubscriberView>> getSubscriberByAccount(
            @PathVariable String account,
            @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<SubscriberView> subscribers = subscriberService.getByAccount(account, pageable);

        if(subscribers.getTotalElements() == 0){
            throw new SubscriberNotExistsException("There are no subscribers with such account");
        }
        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberView> getSubscriberById(@PathVariable("id") Long id){
        SubscriberView foundSubscriber;
        foundSubscriber = subscriberService.getSubscriberById(id);
        if (foundSubscriber == null){
            throw new SubscriberNotExistsException("There is no subscriber with such id");
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
