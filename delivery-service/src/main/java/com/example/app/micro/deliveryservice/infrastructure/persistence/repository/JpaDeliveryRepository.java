package com.example.app.micro.deliveryservice.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.micro.deliveryservice.infrastructure.persistence.entity.DeliveryEntity;

public interface JpaDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);
}
