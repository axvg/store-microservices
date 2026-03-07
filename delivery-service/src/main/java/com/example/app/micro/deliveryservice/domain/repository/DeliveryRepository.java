package com.example.app.micro.deliveryservice.domain.repository;

import java.util.Optional;

import com.example.app.micro.deliveryservice.domain.model.Delivery;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);

    Optional<Delivery> findById(Long id);

    Optional<Delivery> findByOrderId(Long orderId);
}
