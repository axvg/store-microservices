package com.example.app.micro.deliveryservice.application.service;

import org.springframework.stereotype.Service;

import com.example.app.micro.deliveryservice.application.usecase.AssignDriverUseCase;
import com.example.app.micro.deliveryservice.application.usecase.CreateDeliveryUseCase;
import com.example.app.micro.deliveryservice.application.usecase.GetDeliveryByOrderIdUseCase;
import com.example.app.micro.deliveryservice.application.usecase.UpdateDeliveryStatusUseCase;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;
import com.example.app.micro.deliveryservice.infrastructure.messaging.DeliveryEventPublisher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryApplicationService {
    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final AssignDriverUseCase assignDriverUseCase;
    private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
    private final GetDeliveryByOrderIdUseCase getDeliveryByOrderIdUseCase;
    private final DeliveryEventPublisher deliveryEventPublisher;

    @Transactional
    public Delivery createDelivery(Delivery delivery) {
        return createDeliveryUseCase.execute(delivery);
    }

    @Transactional
    public Delivery assignDriver(Long deliveryId, String driverName, Integer estimatedTime) {
        Delivery delivery = assignDriverUseCase.execute(deliveryId, driverName, estimatedTime);
        deliveryEventPublisher.publishDeliveryAssigned(delivery);
        return delivery;
    }

    @Transactional
    public Delivery updateStatus(Long deliveryId, DeliveryStatus status) {
        Delivery delivery = updateDeliveryStatusUseCase.execute(deliveryId, status);
        if (status == DeliveryStatus.DELIVERED) {
            deliveryEventPublisher.publishOrderDelivered(delivery);
        }
        return delivery;
    }

    @Transactional
    public Delivery getByOrderId(Long orderId) {
        return getDeliveryByOrderIdUseCase.execute(orderId);
    }
}
