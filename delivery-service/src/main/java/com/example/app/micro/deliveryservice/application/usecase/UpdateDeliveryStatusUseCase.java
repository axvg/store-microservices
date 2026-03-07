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
public class UpdateDeliveryStatusUseCase {
    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long deliveryId, DeliveryStatus status) {
        if (status == null) {
            throw new InvalidDeliveryDataException("status is required");
        }

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery with id " + deliveryId + " not found"));

        delivery.setStatus(status);
        delivery.setUpdatedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }
}
