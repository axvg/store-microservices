package com.example.app.micro.deliveryservice.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.app.micro.deliveryservice.application.service.OrderGateway;
import com.example.app.micro.deliveryservice.domain.exception.InvalidDeliveryDataException;
import com.example.app.micro.deliveryservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;
import com.example.app.micro.deliveryservice.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateDeliveryUseCase {
    private final DeliveryRepository deliveryRepository;
    private final OrderGateway orderGateway;

    public Delivery execute(Delivery delivery) {
        if (delivery.getOrderId() == null) {
            throw new InvalidDeliveryDataException("orderId is required");
        }
        if (delivery.getAddress() == null || delivery.getAddress().isBlank()) {
            throw new InvalidDeliveryDataException("address is required");
        }
        if (!orderGateway.existsById(delivery.getOrderId())) {
            throw new OrderNotFoundException(delivery.getOrderId());
        }

        delivery.setStatus(DeliveryStatus.CREATED);
        delivery.setEstimatedTime(delivery.getEstimatedTime() == null ? 45 : delivery.getEstimatedTime());
        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }
}
