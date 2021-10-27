package com.gorod.testcase.exception;

public class CannotDeleteServiceException extends RuntimeException{
    public CannotDeleteServiceException(String message) {
        super(message);
    }
}
