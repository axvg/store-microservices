package com.example.app.micro.deliveryservice.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryRequest {
    @NotNull
    private Long orderId;

    @NotBlank
    private String address;

    @Min(1)
    private Integer estimatedTime;
}
