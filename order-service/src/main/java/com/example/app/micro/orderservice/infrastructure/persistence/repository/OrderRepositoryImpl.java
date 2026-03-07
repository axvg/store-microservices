package com.example.app.micro.orderservice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.micro.orderservice.domain.model.Order;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderItemEntity;
import com.example.app.micro.orderservice.infrastructure.persistence.mapper.OrderPersistenceMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository jpaOrderRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        if (entity.getItems() != null) {
            for (OrderItemEntity item : entity.getItems()) {
                item.setOrder(entity);
            }
        }
        return mapper.toDomain(jpaOrderRepository.save(entity));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return mapper.toDomainList(jpaOrderRepository.findByUserId(userId));
    }
}
