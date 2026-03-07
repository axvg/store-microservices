package com.example.app.micro.paymentservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.paymentservice.domain.exception.PaymentNotFoundException;
import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetPaymentByOrderIdUseCase {
    private final PaymentRepository paymentRepository;

    public Payment execute(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment for order " + orderId + " not found"));
    }
}
