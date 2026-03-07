package com.example.app.micro.deliveryservice.application.service;

import org.springframework.stereotype.Service;

import com.example.app.micro.deliveryservice.application.usecase.AssignDriverUseCase;
import com.example.app.micro.deliveryservice.application.usecase.CreateDeliveryUseCase;
import com.example.app.micro.deliveryservice.application.usecase.GetDeliveryByOrderIdUseCase;
import com.example.app.micro.deliveryservice.application.usecase.UpdateDeliveryStatusUseCase;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryApplicationService {
    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final AssignDriverUseCase assignDriverUseCase;
    private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
    private final GetDeliveryByOrderIdUseCase getDeliveryByOrderIdUseCase;

    @Transactional
    public Delivery createDelivery(Delivery delivery) {
        return createDeliveryUseCase.execute(delivery);
    }

    @Transactional
    public Delivery assignDriver(Long deliveryId, String driverName, Integer estimatedTime) {
        return assignDriverUseCase.execute(deliveryId, driverName, estimatedTime);
    }

    @Transactional
    public Delivery updateStatus(Long deliveryId, DeliveryStatus status) {
        return updateDeliveryStatusUseCase.execute(deliveryId, status);
    }

    @Transactional
    public Delivery getByOrderId(Long orderId) {
        return getDeliveryByOrderIdUseCase.execute(orderId);
    }
}
