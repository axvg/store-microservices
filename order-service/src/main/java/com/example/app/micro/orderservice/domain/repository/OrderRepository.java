package com.example.app.micro.orderservice.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.app.micro.orderservice.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUserId(Long userId);
}
