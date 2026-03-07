package com.example.app.micro.deliveryservice.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    private Long id;
    private Long orderId;
    private String address;
    private DeliveryStatus status;
    private Integer estimatedTime;
    private String driverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
