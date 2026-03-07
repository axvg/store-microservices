package com.example.app.micro.paymentservice.domain.repository;

import java.util.Optional;

import com.example.app.micro.paymentservice.domain.model.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByOrderId(Long orderId);
}
