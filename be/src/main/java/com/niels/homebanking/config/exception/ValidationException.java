package com.niels.homebanking.config.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception {

    private final String message;

    private final HttpStatus status;

    public ValidationException(String msg, HttpStatus status) {
        this.message = msg;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
