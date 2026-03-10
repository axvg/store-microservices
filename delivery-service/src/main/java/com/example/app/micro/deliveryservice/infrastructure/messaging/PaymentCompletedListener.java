package com.example.app.micro.deliveryservice.infrastructure.messaging;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import com.example.app.micro.deliveryservice.application.service.DeliveryApplicationService;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class PaymentCompletedListener {
    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedListener.class);

    private final DeliveryApplicationService deliveryApplicationService;

    @KafkaListener(topics = "payments.captured", groupId = "delivery-group")
    public void handlePaymentCompleted(Map<String, Object> paymentPayload) {
        log.info("Received PaymentCompleted event: {}", paymentPayload);
        try {
            Map<String, Object> payload = paymentPayload;
            if (paymentPayload.containsKey("payload") && paymentPayload.get("payload") instanceof Map<?, ?> nestedPayload) {
                payload = (Map<String, Object>) nestedPayload;
            }

            Long orderId = ((Number) payload.get("orderId")).longValue();
            
            Delivery mockDelivery = Delivery.builder()
                .orderId(orderId)
                .address("sample address")
                .status(DeliveryStatus.CREATED)
                .build();
                
            Delivery created = deliveryApplicationService.createDelivery(mockDelivery);
            log.info("Delivery created automatically for orderId: {}", orderId);
            
            // Auto assign driver for stub logic
            deliveryApplicationService.assignDriver(created.getId(), "Driver-Stub", 30);
            log.info("Driver assigned automatically to delivery: {}", created.getId());

        } catch (Exception e) {
            log.error("Error processing PaymentCompleted event", e);
        }
    }
}
