package com.example.app.micro.deliveryservice.domain.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Order with id " + orderId + " not found in order-service");
    }
}
