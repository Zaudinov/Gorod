package com.gorod.testcase.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {
    private String message;
    private HttpStatus status;
    private LocalDateTime dateTime;

    public ApiException(String message, HttpStatus status, LocalDateTime dateTime) {
        this.message = message;
        this.status = status;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", dateTime=" + dateTime +
                '}';
    }
}
