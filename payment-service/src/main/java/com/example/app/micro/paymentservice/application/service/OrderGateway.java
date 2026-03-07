package com.example.app.micro.paymentservice.application.service;

public interface OrderGateway {
    boolean existsById(Long orderId);
}
