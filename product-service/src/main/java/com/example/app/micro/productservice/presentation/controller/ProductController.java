package com.example.app.micro.productservice.presentation.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.productservice.application.service.ProductApplicationService;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.infrastructure.persistence.entity.ProductEntity;
import com.example.app.micro.productservice.infrastructure.persistence.repository.JpaProductRepository;
import com.example.app.micro.productservice.presentation.dto.CreateProductRequest;
import com.example.app.micro.productservice.presentation.dto.ProductResponse;
import com.example.app.micro.productservice.presentation.dto.UpdateProductRequest;
import com.example.app.micro.productservice.presentation.mapper.ProductDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductApplicationService productApplicationService;
    private final ProductDtoMapper productDtoMapper;
    private final JpaProductRepository jpaProductRepository;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("REST request to get all products");
        List<Product> products = productApplicationService.getAllProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts() {
        log.info("REST request to get available products");
        List<Product> products = productApplicationService.getAvailableProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        log.info("REST request to search products: search={}, category={}, minPrice={}, maxPrice={}", search, category, minPrice, maxPrice);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductEntity> productPage = jpaProductRepository.searchProducts(search, category, minPrice, maxPrice, pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(entity -> ProductResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .price(entity.getPrice())
                        .stock(entity.getStock())
                        .category(entity.getCategory())
                        .createdBy(entity.getCreatedBy())
                        .createdAt(entity.getCreatedAt())
                        .updatedAt(entity.getUpdatedAt())
                        .available(entity.getStock() > 0)
                        .build())
                .toList();

        Map<String, Object> response = Map.of(
                "content", content,
                "currentPage", productPage.getNumber(),
                "totalItems", productPage.getTotalElements(),
                "totalPages", productPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        log.info("REST request to get all categories");
        return ResponseEntity.ok(jpaProductRepository.findDistinctCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        log.info("REST request to get product by id: {}", id);
        Product product = productApplicationService.getProductById(id);
        return ResponseEntity.ok(productDtoMapper.toResponse(product));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(@PathVariable Long userId) {
        log.info("REST request to get products by user: {}", userId);
        List<Product> products = productApplicationService.getProductsByUser(userId);
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("REST request to create product: {}", request.getName());
        Product product = productDtoMapper.toDomain(request);
        Product createdProduct = productApplicationService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productDtoMapper.toResponse(createdProduct));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        log.info("REST request to update product with id: {}", id);
        Product product = productDtoMapper.toDomain(request);
        Product updatedProduct = productApplicationService.updateProduct(id, product);
        return ResponseEntity.ok(productDtoMapper.toResponse(updatedProduct));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product with id: {}", id);
        productApplicationService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product Service running");
    }
}
