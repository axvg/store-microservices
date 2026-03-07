package com.example.app.micro.paymentservice.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.app.micro.paymentservice.domain.model.PaymentMethod;
import com.example.app.micro.paymentservice.domain.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime createdAt;
}
