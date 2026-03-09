package com.example.app.micro.paymentservice.infrastructure.messaging;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.app.micro.paymentservice.application.service.PaymentApplicationService;
import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.domain.model.PaymentMethod;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class OrderCreatedListener {
    private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final PaymentApplicationService paymentApplicationService;
    private final PaymentEventPublisher paymentEventPublisher;

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void handleOrderCreated(Map<String, Object> orderPayload) {
        log.info("Received OrderCreated event: {}", orderPayload);
        try {
            Long orderId = ((Number) orderPayload.get("id")).longValue();
            BigDecimal amount = new BigDecimal(String.valueOf(orderPayload.get("totalPrice")));
            
            try {
                paymentApplicationService.getByOrderId(orderId);
                log.info("Payment for order {} already exists. Skipping creation.", orderId);
                return;
            } catch (com.example.app.micro.paymentservice.domain.exception.PaymentNotFoundException e) {
                // Generate mock payment using Stripe stub implicitly handled in PaymentApplicationService or by default
                Payment mockPayment = Payment.builder()
                    .orderId(orderId)
                    .amount(amount)
                    .method(PaymentMethod.CARD)
                    // Just stub simulation
                    .status(com.example.app.micro.paymentservice.domain.model.PaymentStatus.CONFIRMED)
                    .build();
                    
                Payment createdPayment = paymentApplicationService.createPayment(mockPayment);
                createdPayment.setStatus(com.example.app.micro.paymentservice.domain.model.PaymentStatus.CONFIRMED);
                paymentApplicationService.confirmPayment(createdPayment.getId());
                
                log.info("Payment confirmed automatically via mock. Emitting PaymentCompleted.");
                paymentEventPublisher.publishPaymentCompleted(createdPayment);
            }

        } catch (Exception e) {
            log.error("Error processing OrderCreated event", e);
        }
    }
}
