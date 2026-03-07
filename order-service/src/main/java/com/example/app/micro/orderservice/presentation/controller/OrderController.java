package com.example.app.micro.orderservice.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.orderservice.application.service.OrderApplicationService;
import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.presentation.dto.AddOrderItemRequest;
import com.example.app.micro.orderservice.presentation.dto.CreateOrderRequest;
import com.example.app.micro.orderservice.presentation.dto.OrderResponse;
import com.example.app.micro.orderservice.presentation.mapper.OrderDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order created = orderApplicationService.createOrder(orderDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDtoMapper.toResponse(created));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> addItem(
            @PathVariable Long orderId,
            @Valid @RequestBody AddOrderItemRequest request) {
        Order updated = orderApplicationService.addItem(orderId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(orderDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    public ResponseEntity<OrderResponse> removeItem(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        Order updated = orderApplicationService.removeItem(orderId, productId);
        return ResponseEntity.ok(orderDtoMapper.toResponse(updated));
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable Long orderId) {
        Order confirmed = orderApplicationService.confirmOrder(orderId);
        return ResponseEntity.ok(orderDtoMapper.toResponse(confirmed));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderDtoMapper.toResponse(orderApplicationService.getOrderById(orderId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orderApplicationService.getOrdersByUser(userId)));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service running");
    }
}
