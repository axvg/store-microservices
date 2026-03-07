package com.example.app.micro.orderservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetOrderByIdUseCase {
    private final OrderRepository orderRepository;

    public Order execute(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
