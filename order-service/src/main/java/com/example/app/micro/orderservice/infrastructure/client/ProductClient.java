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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductClient implements ProductGateway {
    private final RestTemplate restTemplate;
    private final ProductClientMapper productClientMapper;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Override
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
            return Optional.empty();
        }
    }
}
