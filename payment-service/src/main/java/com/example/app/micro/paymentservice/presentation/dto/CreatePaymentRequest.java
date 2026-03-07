package com.example.app.micro.paymentservice.presentation.dto;

import java.math.BigDecimal;

import com.example.app.micro.paymentservice.domain.model.PaymentMethod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    @NotNull
    private Long orderId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;
}
