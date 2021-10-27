package com.gorod.testcase.exception;

public class ServiceNotExistsException extends RuntimeException{
    public ServiceNotExistsException(String message) {
        super(message);
    }
}
