package com.example.app.micro.deliveryservice.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.presentation.dto.CreateDeliveryRequest;
import com.example.app.micro.deliveryservice.presentation.dto.DeliveryResponse;

@Mapper(componentModel = "spring")
public interface DeliveryDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "driverName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Delivery toDomain(CreateDeliveryRequest request);

    DeliveryResponse toResponse(Delivery delivery);
}
