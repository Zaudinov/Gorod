package com.gorod.testcase.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SubscriberNotExistsException.class)
    public ResponseEntity<Object> handleSubscriberNotFound(
            SubscriberNotExistsException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                                            ex.getMessage(),
                                            HttpStatus.BAD_REQUEST,
                                            LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotExistsException.class)
    public ResponseEntity<Object> handleServiceNotFound(
            ServiceNotExistsException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotDeleteServiceException.class)
    public ResponseEntity<Object> handleServiceDeletingException(
            CannotDeleteServiceException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()), HttpStatus.CONFLICT);
    }
}
