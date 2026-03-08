package com.example.app.micro.orderservice.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    // Explicit Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public static OrderItemEntityBuilder builder() {
        return new OrderItemEntityBuilder();
    }

    public static class OrderItemEntityBuilder {
        private Long id;
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
        private OrderEntity order;
        
        public OrderItemEntityBuilder id(Long id) { this.id = id; return this; }
        public OrderItemEntityBuilder productId(Long productId) { this.productId = productId; return this; }
        public OrderItemEntityBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public OrderItemEntityBuilder price(BigDecimal price) { this.price = price; return this; }
        public OrderItemEntityBuilder order(OrderEntity order) { this.order = order; return this; }
        
        public OrderItemEntity build() {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setId(this.id);
            orderItemEntity.setProductId(this.productId);
            orderItemEntity.setQuantity(this.quantity);
            orderItemEntity.setPrice(this.price);
            orderItemEntity.setOrder(this.order);
            return orderItemEntity;
        }
    }
}
