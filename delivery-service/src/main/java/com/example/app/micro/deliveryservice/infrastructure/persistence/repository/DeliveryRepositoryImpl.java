package com.example.app.micro.deliveryservice.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.micro.deliveryservice.domain.model.Delivery;
import com.example.app.micro.deliveryservice.domain.repository.DeliveryRepository;
import com.example.app.micro.deliveryservice.infrastructure.persistence.mapper.DeliveryPersistenceMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {
    private final JpaDeliveryRepository jpaDeliveryRepository;
    private final DeliveryPersistenceMapper mapper;

    @Override
    public Delivery save(Delivery delivery) {
        return mapper.toDomain(jpaDeliveryRepository.save(mapper.toEntity(delivery)));
    }

    @Override
    public Optional<Delivery> findById(Long id) {
        return jpaDeliveryRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Delivery> findByOrderId(Long orderId) {
        return jpaDeliveryRepository.findByOrderId(orderId).map(mapper::toDomain);
    }
}
