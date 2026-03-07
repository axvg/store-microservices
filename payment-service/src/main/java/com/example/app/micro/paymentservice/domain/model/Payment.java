package com.example.app.micro.paymentservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime createdAt;
}
