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

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {
    @Mapping(target = "items", source = "items")
    Order toDomain(OrderEntity entity);

    @Mapping(target = "items", source = "items")
    OrderEntity toEntity(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItemEntity toEntity(OrderItem item);

    OrderItem toDomain(OrderItemEntity entity);

    List<Order> toDomainList(List<OrderEntity> entities);

    @AfterMapping
    default void bindItems(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getItems() == null) {
            return;
        }
        for (OrderItemEntity itemEntity : orderEntity.getItems()) {
            itemEntity.setOrder(orderEntity);
        }
    }
}
