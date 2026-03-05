package com.example.app.micro.productservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getters, Setters, toString, equals, hashCode
@Builder // Product.builder().id(1L).name("Product 1").build()
@NoArgsConstructor // new Product()
@AllArgsConstructor // new Product(1L, "Product 1", "Description", new BigDecimal("9.99"), 100, "Category", 1L, LocalDateTime.now(), LocalDateTime.now(), null)
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User createdByUser;

    public boolean isValid() {
        return (
            name != null &&
            !name.trim().isEmpty() &&
            price != null &&
            price.compareTo(BigDecimal.ZERO) > 0 &&
            stock != null &&
            stock >= 0
        );
    }

    public boolean isAvailable() {
        return (
            stock != null &&
            stock > 0
        );
    };

    public void reduceStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (stock == null || stock < quantity) {
            throw new IllegalStateException("Not enough stock available");
        }
        stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        stock = (stock == null) ? quantity : stock + quantity;
    }
}
