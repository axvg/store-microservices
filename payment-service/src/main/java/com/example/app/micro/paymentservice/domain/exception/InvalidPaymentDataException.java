package com.example.app.micro.paymentservice.domain.exception;

public class InvalidPaymentDataException extends RuntimeException {
    public InvalidPaymentDataException(String message) {
        super(message);
    }
}
