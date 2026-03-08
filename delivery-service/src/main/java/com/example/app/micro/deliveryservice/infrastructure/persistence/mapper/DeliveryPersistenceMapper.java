package com.example.app.micro.deliveryservice.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.infrastructure.persistence.entity.DeliveryEntity;

@Mapper(componentModel = "spring")
public abstract class DeliveryPersistenceMapper {
    public Delivery toDomain(DeliveryEntity entity) {
        if (entity == null) return null;
        Delivery domain = new Delivery();
        domain.setId(entity.getId());
        domain.setOrderId(entity.getOrderId());
        domain.setAddress(entity.getAddress());
        domain.setStatus(entity.getStatus());
        domain.setEstimatedTime(entity.getEstimatedTime());
        domain.setDriverName(entity.getDriverName());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public DeliveryEntity toEntity(Delivery delivery) {
        if (delivery == null) return null;
        DeliveryEntity entity = new DeliveryEntity();
        entity.setId(delivery.getId());
        entity.setOrderId(delivery.getOrderId());
        entity.setAddress(delivery.getAddress());
        entity.setStatus(delivery.getStatus());
        entity.setEstimatedTime(delivery.getEstimatedTime());
        entity.setDriverName(delivery.getDriverName());
        entity.setCreatedAt(delivery.getCreatedAt());
        entity.setUpdatedAt(delivery.getUpdatedAt());
        return entity;
    }
}
