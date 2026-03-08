package com.example.app.micro.paymentservice.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.app.micro.paymentservice.domain.model.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishPaymentCompleted(Payment payment) {
        log.info("Publishing PaymentCompleted event for order id: {}", payment.getOrderId());
        kafkaTemplate.send("payment-completed", String.valueOf(payment.getOrderId()), payment);
    }
}
