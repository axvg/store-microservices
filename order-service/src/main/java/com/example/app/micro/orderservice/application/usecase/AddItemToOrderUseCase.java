package com.example.app.micro.orderservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.application.service.ProductGateway;
import com.example.app.micro.orderservice.application.service.ProductSnapshot;
import com.example.app.micro.orderservice.domain.exception.ExternalServiceException;
import com.example.app.micro.orderservice.domain.exception.InvalidOrderDataException;
import com.example.app.micro.orderservice.domain.exception.OrderNotFoundException;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.model.OrderItem;
import com.example.app.micro.orderservice.domain.model.OrderStatus;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddItemToOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductGateway productGateway;

    public Order execute(Long orderId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidOrderDataException("Item quantity must be greater than zero");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (OrderStatus.CONFIRMED.equals(order.getStatus())) {
            throw new InvalidOrderDataException("Cannot add items to a confirmed order");
        }

        ProductSnapshot product = productGateway.getProductById(productId)
                .orElseThrow(() -> new ExternalServiceException("Product not found in product-service"));

        if (!product.available()) {
            throw new InvalidOrderDataException("Product " + product.id() + " is not available");
        }

        order.addItem(OrderItem.builder()
                .productId(product.id())
                .quantity(quantity)
                .price(product.price())
                .build());

        return orderRepository.save(order);
    }
}
