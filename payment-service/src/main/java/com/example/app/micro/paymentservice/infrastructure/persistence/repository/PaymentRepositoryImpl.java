package com.example.app.micro.paymentservice.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.domain.repository.PaymentRepository;
import com.example.app.micro.paymentservice.infrastructure.persistence.mapper.PaymentPersistenceMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentPersistenceMapper mapper;

    @Override
    public Payment save(Payment payment) {
        return mapper.toDomain(jpaPaymentRepository.save(mapper.toEntity(payment)));
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaPaymentRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return jpaPaymentRepository.findByOrderId(orderId).map(mapper::toDomain);
    }
}
