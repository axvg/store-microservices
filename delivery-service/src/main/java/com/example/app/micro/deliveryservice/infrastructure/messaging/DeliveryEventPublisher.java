package com.example.app.micro.deliveryservice.infrastructure.messaging;

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
        kafkaTemplate.send("delivery-assigned", String.valueOf(delivery.getId()), delivery);
    }
    
    public void publishOrderDelivered(Delivery delivery) {
        log.info("Publishing OrderDelivered event for delivery id: {}", delivery.getId());
        kafkaTemplate.send("order-delivered", String.valueOf(delivery.getId()), delivery);
    }
}
