package com.example.app.micro.orderservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.domain.exception.InvalidOrderDataException;
import com.example.app.micro.orderservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConfirmOrderUseCase {
    private final OrderRepository orderRepository;

    public Order execute(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new InvalidOrderDataException("Order must contain at least one item");
        }

        order.confirm();
        return orderRepository.save(order);
    }
}
