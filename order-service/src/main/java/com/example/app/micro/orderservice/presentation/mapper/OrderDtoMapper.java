package com.example.app.micro.orderservice.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.model.OrderItem;
import com.example.app.micro.orderservice.presentation.dto.CreateOrderItemRequest;
import com.example.app.micro.orderservice.presentation.dto.CreateOrderRequest;
import com.example.app.micro.orderservice.presentation.dto.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toDomain(CreateOrderRequest request);

    @Mapping(target = "price", ignore = true)
    OrderItem toDomain(CreateOrderItemRequest request);

    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);
}
