package com.example.app.micro.paymentservice.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.example.app.micro.paymentservice.domain.model.Payment;
import com.example.app.micro.paymentservice.infrastructure.persistence.entity.PaymentEntity;

@Mapper(componentModel = "spring")
public interface PaymentPersistenceMapper {
    Payment toDomain(PaymentEntity entity);

    PaymentEntity toEntity(Payment payment);
}
