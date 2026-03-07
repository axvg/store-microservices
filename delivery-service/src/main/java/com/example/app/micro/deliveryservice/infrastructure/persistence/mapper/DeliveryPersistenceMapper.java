package com.example.app.micro.deliveryservice.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.infrastructure.persistence.entity.DeliveryEntity;

@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {
    Delivery toDomain(DeliveryEntity entity);

    DeliveryEntity toEntity(Delivery delivery);
}
