package com.example.app.micro.productservice.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.micro.productservice.infrastructure.persistence.entity.ProductEntity;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(String category);

    List<ProductEntity> findByCreatedBy(Long userId);

    @Query("SELECT p FROM ProductEntity p WHERE p.stock > 0")
    List<ProductEntity> findAvailableProducts();

    List<ProductEntity> findByName(String name);

    @Query("SELECT p FROM ProductEntity p WHERE p.stock > 0 " +
           "AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:category IS NULL OR p.category = :category) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<ProductEntity> searchProducts(
            @Param("search") String search,
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    @Query("SELECT DISTINCT p.category FROM ProductEntity p WHERE p.category IS NOT NULL ORDER BY p.category")
    List<String> findDistinctCategories();
}
