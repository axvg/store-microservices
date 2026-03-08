package com.example.app.micro.orderservice.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.model.OrderItem;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderItemEntity;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderPersistenceMapper {
    
    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;
        Order order = new Order();
        order.setId(entity.getId());
        order.setUserId(entity.getUserId());
        order.setStatus(entity.getStatus());
        order.setTotalPrice(entity.getTotalPrice());
        order.setCreatedAt(entity.getCreatedAt());
        if (entity.getItems() != null) {
            order.setItems(entity.getItems().stream().map(this::toDomain).collect(Collectors.toList()));
        }
        return order;
    }

    public OrderEntity toEntity(Order order) {
        if (order == null) return null;
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setUserId(order.getUserId());
        entity.setStatus(order.getStatus());
        entity.setTotalPrice(order.getTotalPrice());
        entity.setCreatedAt(order.getCreatedAt());
        if (order.getItems() != null) {
            entity.setItems(order.getItems().stream().map(this::toEntity).collect(Collectors.toList()));
        }
        bindItems(entity);
        return entity;
    }

    public OrderItemEntity toEntity(OrderItem item) {
        if (item == null) return null;
        OrderItemEntity entity = new OrderItemEntity();
        entity.setProductId(item.getProductId());
        entity.setQuantity(item.getQuantity());
        entity.setPrice(item.getPrice());
        return entity;
    }

    public OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) return null;
        OrderItem item = new OrderItem();
        item.setProductId(entity.getProductId());
        item.setQuantity(entity.getQuantity());
        item.setPrice(entity.getPrice());
        return item;
    }

    public abstract List<Order> toDomainList(List<OrderEntity> entities);

    @AfterMapping
    protected void bindItems(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getItems() == null) {
            return;
        }
        for (OrderItemEntity itemEntity : orderEntity.getItems()) {
            itemEntity.setOrder(orderEntity);
        }
    }
}
