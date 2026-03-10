package com.example.app.micro.orderservice.infrastructure.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.domain.model.Order;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishOrderCreated(Order order) {
        log.info("Publishing OrderCreated event for order id: {}", order.getId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", order.getId());
        payload.put("userId", order.getUserId());
        payload.put("status", order.getStatus().name());
        payload.put("totalPrice", order.getTotalPrice());

        kafkaTemplate.send(
                "orders.created",
                String.valueOf(order.getId()),
                EventEnvelope.of("orders.created", String.valueOf(order.getId()), payload));
    }

    public void publishOrderConfirmed(Order order) {
        log.info("Publishing OrderConfirmed event for order id: {}", order.getId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", order.getId());
        payload.put("userId", order.getUserId());
        payload.put("status", order.getStatus().name());
        payload.put("totalPrice", order.getTotalPrice());

        kafkaTemplate.send(
                "orders.confirmed",
                String.valueOf(order.getId()),
                EventEnvelope.of("orders.confirmed", String.valueOf(order.getId()), payload));
    }
}
