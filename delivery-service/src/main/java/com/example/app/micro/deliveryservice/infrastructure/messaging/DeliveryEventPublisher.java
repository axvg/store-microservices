package com.example.app.micro.deliveryservice.infrastructure.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.app.micro.deliveryservice.domain.model.Delivery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class DeliveryEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(DeliveryEventPublisher.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishDeliveryAssigned(Delivery delivery) {
        log.info("Publishing DeliveryAssigned event for delivery id: {}", delivery.getId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", delivery.getId());
        payload.put("orderId", delivery.getOrderId());
        payload.put("status", delivery.getStatus().name());
        payload.put("driverName", delivery.getDriverName());
        payload.put("estimatedTime", delivery.getEstimatedTime());

        kafkaTemplate.send(
                "deliveries.assigned",
                String.valueOf(delivery.getOrderId()),
                EventEnvelope.of("deliveries.assigned", String.valueOf(delivery.getOrderId()), payload));
    }
    
    public void publishStatusChanged(Delivery delivery) {
        log.info("Publishing DeliveryStatusChanged event for delivery id: {}", delivery.getId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", delivery.getId());
        payload.put("orderId", delivery.getOrderId());
        payload.put("status", delivery.getStatus().name());
        payload.put("driverName", delivery.getDriverName());
        payload.put("estimatedTime", delivery.getEstimatedTime());

        kafkaTemplate.send(
                "deliveries.status.changed",
                String.valueOf(delivery.getOrderId()),
                EventEnvelope.of("deliveries.status.changed", String.valueOf(delivery.getOrderId()), payload));
    }
}
