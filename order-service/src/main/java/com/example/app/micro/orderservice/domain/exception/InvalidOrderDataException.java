package com.example.app.micro.orderservice.domain.exception;

public class InvalidOrderDataException extends RuntimeException {
    public InvalidOrderDataException(String message) {
        super(message);
    }
}
