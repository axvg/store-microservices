package com.example.app.micro.deliveryservice.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    private Long id;
    private Long orderId;
    private String address;
    private DeliveryStatus status;
    private Integer estimatedTime;
    private String driverName;
    private LocalDateTime createdAt;
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

    // Builder explicit
    public static DeliveryBuilder builder() {
        return new DeliveryBuilder();
    }

    public static class DeliveryBuilder {
        private Long id;
        private Long orderId;
        private String address;
        private DeliveryStatus status;
        private Integer estimatedTime;
        private String driverName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public DeliveryBuilder id(Long id) { this.id = id; return this; }
        public DeliveryBuilder orderId(Long orderId) { this.orderId = orderId; return this; }
        public DeliveryBuilder address(String address) { this.address = address; return this; }
        public DeliveryBuilder status(DeliveryStatus status) { this.status = status; return this; }
        public DeliveryBuilder estimatedTime(Integer estimatedTime) { this.estimatedTime = estimatedTime; return this; }
        public DeliveryBuilder driverName(String driverName) { this.driverName = driverName; return this; }
        public DeliveryBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public DeliveryBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        
        public Delivery build() {
            Delivery delivery = new Delivery();
            delivery.setId(this.id);
            delivery.setOrderId(this.orderId);
            delivery.setAddress(this.address);
            delivery.setStatus(this.status);
            delivery.setEstimatedTime(this.estimatedTime);
            delivery.setDriverName(this.driverName);
            delivery.setCreatedAt(this.createdAt);
            delivery.setUpdatedAt(this.updatedAt);
            return delivery;
        }
    }
}
