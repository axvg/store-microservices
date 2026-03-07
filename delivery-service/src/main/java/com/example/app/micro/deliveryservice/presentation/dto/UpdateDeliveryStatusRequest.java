package com.example.app.micro.deliveryservice.presentation.dto;

import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryStatusRequest {
    @NotNull
    private DeliveryStatus status;
}
