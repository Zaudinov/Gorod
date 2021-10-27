package com.gorod.testcase.exception;

public class SubscriberNotExistsException extends RuntimeException{
    public SubscriberNotExistsException(String message) {
        super(message);
    }
}
