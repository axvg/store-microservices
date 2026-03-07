package com.example.app.micro.paymentservice.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.presentation.dto.CreatePaymentRequest;
import com.example.app.micro.paymentservice.presentation.dto.PaymentResponse;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Payment toDomain(CreatePaymentRequest request);

    PaymentResponse toResponse(Payment payment);
}
