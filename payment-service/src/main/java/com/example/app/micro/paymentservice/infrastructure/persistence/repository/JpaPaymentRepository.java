package com.example.app.micro.paymentservice.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.micro.paymentservice.infrastructure.persistence.entity.PaymentEntity;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByOrderId(Long orderId);
}
