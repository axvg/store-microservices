package com.example.app.micro.productservice.domain.repository;

import com.example.app.micro.productservice.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);

    List<Product> findByCreatedBy(Long userId);

    List<Product> findAvailableProducts();

    Product save(Product product);

    void deleteById(Long id);

    boolean existsById(Long id);
}
