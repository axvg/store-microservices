package com.example.app.micro.orderservice.infrastructure.client.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductClientDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean available;
}
