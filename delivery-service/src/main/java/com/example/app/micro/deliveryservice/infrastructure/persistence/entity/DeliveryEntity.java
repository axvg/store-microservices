package com.example.app.micro.deliveryservice.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import com.example.app.micro.deliveryservice.domain.model.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DeliveryStatus status;

    @Column(nullable = false)
    private Integer estimatedTime;

    @Column(length = 120)
    private String driverName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Explicit Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public DeliveryStatus getStatus() { return status; }
    public void setStatus(DeliveryStatus status) { this.status = status; }
    
    public Integer getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(Integer estimatedTime) { this.estimatedTime = estimatedTime; }
    
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static DeliveryEntityBuilder builder() {
        return new DeliveryEntityBuilder();
    }

    public static class DeliveryEntityBuilder {
        private Long id;
        private Long orderId;
        private String address;
        private DeliveryStatus status;
        private Integer estimatedTime;
        private String driverName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public DeliveryEntityBuilder id(Long id) { this.id = id; return this; }
        public DeliveryEntityBuilder orderId(Long orderId) { this.orderId = orderId; return this; }
        public DeliveryEntityBuilder address(String address) { this.address = address; return this; }
        public DeliveryEntityBuilder status(DeliveryStatus status) { this.status = status; return this; }
        public DeliveryEntityBuilder estimatedTime(Integer estimatedTime) { this.estimatedTime = estimatedTime; return this; }
        public DeliveryEntityBuilder driverName(String driverName) { this.driverName = driverName; return this; }
        public DeliveryEntityBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public DeliveryEntityBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        
        public DeliveryEntity build() {
            DeliveryEntity entity = new DeliveryEntity();
            entity.setId(this.id);
            entity.setOrderId(this.orderId);
            entity.setAddress(this.address);
            entity.setStatus(this.status);
            entity.setEstimatedTime(this.estimatedTime);
            entity.setDriverName(this.driverName);
            entity.setCreatedAt(this.createdAt);
            entity.setUpdatedAt(this.updatedAt);
            return entity;
        }
    }
}
