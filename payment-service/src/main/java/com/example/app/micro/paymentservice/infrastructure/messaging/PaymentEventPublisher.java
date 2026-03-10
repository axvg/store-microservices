package com.example.app.micro.paymentservice.infrastructure.messaging;

import java.util.HashMap;
import java.util.Map;

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
    
    public void publishPaymentCaptured(Payment payment) {
        log.info("Publishing PaymentCaptured event for order id: {}", payment.getOrderId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", payment.getId());
        payload.put("orderId", payment.getOrderId());
        payload.put("amount", payment.getAmount());
        payload.put("status", payment.getStatus().name());
        payload.put("method", payment.getMethod().name());

        kafkaTemplate.send(
                "payments.captured",
                String.valueOf(payment.getOrderId()),
                EventEnvelope.of("payments.captured", String.valueOf(payment.getOrderId()), payload));
    }
}
