package com.example.app.micro.orderservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.domain.exception.InvalidOrderDataException;
import com.example.app.micro.orderservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.model.OrderStatus;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveItemFromOrderUseCase {
    private final OrderRepository orderRepository;

    public Order execute(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (OrderStatus.CONFIRMED.equals(order.getStatus())) {
            throw new InvalidOrderDataException("Cannot remove items from a confirmed order");
        }

        boolean removed = order.removeItem(productId);
        if (!removed) {
            throw new InvalidOrderDataException("Product " + productId + " not found in order");
        }

        return orderRepository.save(order);
    }
}
