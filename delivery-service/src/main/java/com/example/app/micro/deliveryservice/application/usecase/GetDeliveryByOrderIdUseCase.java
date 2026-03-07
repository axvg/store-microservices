package com.example.app.micro.deliveryservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.deliveryservice.domain.exception.DeliveryNotFoundException;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetDeliveryByOrderIdUseCase {
    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery for order " + orderId + " not found"));
    }
}
