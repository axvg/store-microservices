package com.example.app.micro.orderservice.application.service;

import java.math.BigDecimal;

public record ProductSnapshot(Long id, String name, BigDecimal price, boolean available) {
}
