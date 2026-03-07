package com.example.app.micro.orderservice.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.application.service.UserGateway;
import com.example.app.micro.orderservice.domain.exception.ExternalServiceException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetOrdersByUserUseCase {
    private final OrderRepository orderRepository;
    private final UserGateway userGateway;

    public List<Order> execute(Long userId) {
        if (userGateway.getUserById(userId).isEmpty()) {
            throw new ExternalServiceException("User not found in user-service");
        }
        return orderRepository.findByUserId(userId);
    }
}
