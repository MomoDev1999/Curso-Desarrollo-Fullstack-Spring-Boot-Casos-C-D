package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class XNotFoundException extends RuntimeException {

    public XNotFoundException(String message) {
        super(message);
    }

    public XNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
