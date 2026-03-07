package com.example.app.micro.deliveryservice.domain.exception;

public class InvalidDeliveryDataException extends RuntimeException {
    public InvalidDeliveryDataException(String message) {
        super(message);
    }
}
