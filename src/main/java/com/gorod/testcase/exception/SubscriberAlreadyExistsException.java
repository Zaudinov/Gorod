package com.gorod.testcase.exception;

public class SubscriberAlreadyExistsException extends RuntimeException{
    public SubscriberAlreadyExistsException(String message) {
        super(message);
    }
}
