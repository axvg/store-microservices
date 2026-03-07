package com.example.app.micro.orderservice.application.service;

import java.util.Optional;

public interface ProductGateway {
    Optional<ProductSnapshot> getProductById(Long productId);
}
