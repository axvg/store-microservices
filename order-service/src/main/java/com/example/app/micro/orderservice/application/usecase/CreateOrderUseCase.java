package com.example.app.micro.orderservice.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.application.service.ProductGateway;
import com.example.app.micro.orderservice.application.service.ProductSnapshot;
import com.example.app.micro.orderservice.application.service.UserGateway;
import com.example.app.micro.orderservice.domain.exception.ExternalServiceException;
import com.example.app.micro.orderservice.domain.exception.InvalidOrderDataException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.model.OrderItem;
import com.example.app.micro.orderservice.domain.model.OrderStatus;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;
import com.example.app.micro.orderservice.infrastructure.messaging.OrderEventPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final OrderEventPublisher orderEventPublisher;

    public Order execute(Order order) {
        if (order.getUserId() == null) {
            throw new InvalidOrderDataException("userId is required");
        }
        if (userGateway.getUserById(order.getUserId()).isEmpty()) {
            throw new ExternalServiceException("User not found in user-service");
        }

        List<OrderItem> items = order.getItems() == null ? List.of() : order.getItems();
        List<OrderItem> validatedItems = new ArrayList<>();

        for (OrderItem item : items) {
            validateItemQuantity(item.getQuantity());
            ProductSnapshot product = productGateway.getProductById(item.getProductId())
                    .orElseThrow(() -> new ExternalServiceException("Product not found in product-service"));
            if (!product.available()) {
                throw new InvalidOrderDataException("Product " + product.id() + " is not available");
            }
            validatedItems.add(OrderItem.builder()
                    .productId(product.id())
                    .quantity(item.getQuantity())
                    .price(product.price())
                    .build());
        }

        order.setItems(validatedItems);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(BigDecimal.ZERO);
        order.recalculateTotal();

        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publishOrderCreated(savedOrder);
        
        return savedOrder;
    }

    private void validateItemQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidOrderDataException("Item quantity must be greater than zero");
        }
    }
}
