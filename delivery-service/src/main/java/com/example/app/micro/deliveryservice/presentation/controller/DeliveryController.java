package com.example.app.micro.deliveryservice.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.deliveryservice.application.service.DeliveryApplicationService;
import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.presentation.dto.AssignDriverRequest;
import com.example.app.micro.deliveryservice.presentation.dto.CreateDeliveryRequest;
import com.example.app.micro.deliveryservice.presentation.dto.DeliveryResponse;
import com.example.app.micro.deliveryservice.presentation.dto.UpdateDeliveryStatusRequest;
import com.example.app.micro.deliveryservice.presentation.mapper.DeliveryDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryApplicationService deliveryApplicationService;
    private final DeliveryDtoMapper deliveryDtoMapper;

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody CreateDeliveryRequest request) {
        Delivery created = deliveryApplicationService.createDelivery(deliveryDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryDtoMapper.toResponse(created));
    }

    @PutMapping("/{deliveryId}/assign-driver")
    public ResponseEntity<DeliveryResponse> assignDriver(
            @PathVariable Long deliveryId,
            @Valid @RequestBody AssignDriverRequest request) {
        Delivery updated = deliveryApplicationService.assignDriver(
                deliveryId,
                request.getDriverName(),
                request.getEstimatedTime());
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(updated));
    }

    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<DeliveryResponse> updateStatus(
            @PathVariable Long deliveryId,
            @Valid @RequestBody UpdateDeliveryStatusRequest request) {
        Delivery updated = deliveryApplicationService.updateStatus(deliveryId, request.getStatus());
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(updated));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliveryResponse> getByOrderId(@PathVariable Long orderId) {
        Delivery delivery = deliveryApplicationService.getByOrderId(orderId);
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(delivery));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Delivery Service running");
    }
}
