package com.example.app.micro.deliveryservice.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignDriverRequest {
    @NotBlank
    private String driverName;

    @Min(1)
    private Integer estimatedTime;
}
