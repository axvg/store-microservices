package com.example.app.micro.orderservice.infrastructure.client.mapper;

import org.mapstruct.Mapper;

import com.example.app.micro.orderservice.application.service.UserSnapshot;
import com.example.app.micro.orderservice.infrastructure.client.dto.UserClientDto;

@Mapper(componentModel = "spring")
public interface UserClientMapper {
    UserSnapshot toSnapshot(UserClientDto dto);
}
