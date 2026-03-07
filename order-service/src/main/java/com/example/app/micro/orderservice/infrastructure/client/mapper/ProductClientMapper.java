package com.example.app.micro.orderservice.infrastructure.client.mapper;

import org.mapstruct.Mapper;

import com.example.app.micro.orderservice.application.service.ProductSnapshot;
import com.example.app.micro.orderservice.infrastructure.client.dto.ProductClientDto;

@Mapper(componentModel = "spring")
public interface ProductClientMapper {
    ProductSnapshot toSnapshot(ProductClientDto dto);
}
