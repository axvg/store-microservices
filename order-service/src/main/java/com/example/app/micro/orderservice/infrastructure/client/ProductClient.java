package com.example.app.micro.orderservice.infrastructure.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.micro.orderservice.application.service.ProductGateway;
import com.example.app.micro.orderservice.application.service.ProductSnapshot;
import com.example.app.micro.orderservice.infrastructure.client.dto.ProductClientDto;
import com.example.app.micro.orderservice.infrastructure.client.mapper.ProductClientMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class ProductClient implements ProductGateway {
    private static final Logger log = LoggerFactory.getLogger(ProductClient.class);
    private final RestTemplate restTemplate;
    private final ProductClientMapper productClientMapper;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProductById")
    @Retry(name = "productService")
    public Optional<ProductSnapshot> getProductById(Long productId) {
        try {
            ResponseEntity<ProductClientDto> response = restTemplate.getForEntity(
                    productServiceUrl + "/api/products/" + productId,
                    ProductClientDto.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return Optional.empty();
            }
            return Optional.of(productClientMapper.toSnapshot(response.getBody()));
        } catch (Exception ex) {
            log.error("Error getting product {}", productId, ex);
            return Optional.empty();
        }
    }

    public Optional<ProductSnapshot> fallbackGetProductById(Long productId, Throwable t) {
        log.warn("Fallback triggered for getting product {} due to {}", productId, t.getMessage());
        return Optional.empty();
    }
}
