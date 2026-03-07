package com.example.app.micro.deliveryservice.application.service;

public interface OrderGateway {
    boolean existsById(Long orderId);
}
