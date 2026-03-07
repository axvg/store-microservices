package com.example.app.micro.paymentservice.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.app.micro.paymentservice.application.service.OrderGateway;
import com.example.app.micro.paymentservice.domain.exception.InvalidPaymentDataException;
import com.example.app.micro.paymentservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.domain.model.PaymentStatus;
import com.example.app.micro.paymentservice.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final OrderGateway orderGateway;

    public Payment execute(Payment payment) {
        if (payment.getOrderId() == null) {
            throw new InvalidPaymentDataException("orderId is required");
        }
        if (!orderGateway.existsById(payment.getOrderId())) {
            throw new OrderNotFoundException(payment.getOrderId());
        }
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentDataException("amount must be greater than zero");
        }
        if (payment.getMethod() == null) {
            throw new InvalidPaymentDataException("method is required");
        }

        payment.setStatus(PaymentStatus.CREATED);
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}
