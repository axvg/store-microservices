package com.example.app.micro.orderservice.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.app.micro.orderservice.domain.model.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    // Explicit Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<OrderItemEntity> getItems() { return items; }
    public void setItems(List<OrderItemEntity> items) { this.items = items; }

    public static OrderEntityBuilder builder() {
        return new OrderEntityBuilder();
    }

    public static class OrderEntityBuilder {
        private Long id;
        private Long userId;
        private OrderStatus status;
        private BigDecimal totalPrice;
        private LocalDateTime createdAt;
        private List<OrderItemEntity> items = new ArrayList<>();
        
        public OrderEntityBuilder id(Long id) { this.id = id; return this; }
        public OrderEntityBuilder userId(Long userId) { this.userId = userId; return this; }
        public OrderEntityBuilder status(OrderStatus status) { this.status = status; return this; }
        public OrderEntityBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public OrderEntityBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderEntityBuilder items(List<OrderItemEntity> items) { this.items = items; return this; }
        
        public OrderEntity build() {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(this.id);
            orderEntity.setUserId(this.userId);
            orderEntity.setStatus(this.status);
            orderEntity.setTotalPrice(this.totalPrice);
            orderEntity.setCreatedAt(this.createdAt);
            orderEntity.setItems(this.items);
            return orderEntity;
        }
    }
}
