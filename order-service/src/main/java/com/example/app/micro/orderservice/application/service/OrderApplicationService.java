package com.example.app.micro.orderservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.micro.orderservice.application.usecase.AddItemToOrderUseCase;
import com.example.app.micro.orderservice.application.usecase.ConfirmOrderUseCase;
import com.example.app.micro.orderservice.application.usecase.CreateOrderUseCase;
import com.example.app.micro.orderservice.application.usecase.GetOrderByIdUseCase;
import com.example.app.micro.orderservice.application.usecase.GetOrdersByUserUseCase;
import com.example.app.micro.orderservice.application.usecase.RemoveItemFromOrderUseCase;
import com.example.app.micro.orderservice.domain.model.Order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {
    private final CreateOrderUseCase createOrderUseCase;
    private final AddItemToOrderUseCase addItemToOrderUseCase;
    private final RemoveItemFromOrderUseCase removeItemFromOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;

    @Transactional
    public Order createOrder(Order order) {
        return createOrderUseCase.execute(order);
    }

    @Transactional
    public Order addItem(Long orderId, Long productId, Integer quantity) {
        return addItemToOrderUseCase.execute(orderId, productId, quantity);
    }

    @Transactional
    public Order removeItem(Long orderId, Long productId) {
        return removeItemFromOrderUseCase.execute(orderId, productId);
    }

    @Transactional
    public Order confirmOrder(Long orderId) {
        return confirmOrderUseCase.execute(orderId);
    }

    @Transactional
    public Order getOrderById(Long orderId) {
        return getOrderByIdUseCase.execute(orderId);
    }

    @Transactional
    public List<Order> getOrdersByUser(Long userId) {
        return getOrdersByUserUseCase.execute(userId);
    }
}
