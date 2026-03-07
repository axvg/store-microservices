package com.example.app.micro.deliveryservice.presentation.dto;

import java.time.LocalDateTime;

import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private String address;
    private DeliveryStatus status;
    private Integer estimatedTime;
    private String driverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
