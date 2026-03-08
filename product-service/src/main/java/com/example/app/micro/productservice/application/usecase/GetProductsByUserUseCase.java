package com.example.app.micro.productservice.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductsByUserUseCase {
    private final ProductRepository repo;

    public List<Product> execute(Long userId) {
        log.debug("Executing GetProductsByUserUseCase with userId: {}", userId);
        return repo.findByCreatedBy(userId);
    }

}