package com.example.app.micro.deliveryservice.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.app.micro.deliveryservice.domain.exception.DeliveryNotFoundException;
import com.example.app.micro.deliveryservice.domain.exception.InvalidDeliveryDataException;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;
import com.example.app.micro.deliveryservice.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AssignDriverUseCase {
    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long deliveryId, String driverName, Integer estimatedTime) {
        if (driverName == null || driverName.isBlank()) {
            throw new InvalidDeliveryDataException("driverName is required");
        }

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery with id " + deliveryId + " not found"));

        if (estimatedTime != null && estimatedTime <= 0) {
            throw new InvalidDeliveryDataException("estimatedTime must be greater than zero");
        }

        delivery.setDriverName(driverName);
        if (estimatedTime != null) {
            delivery.setEstimatedTime(estimatedTime);
        }
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setUpdatedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }
}
