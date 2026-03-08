package com.example.app.micro.productservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.productservice.domain.exception.ProductNotFoundException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdUseCase {
    private final ProductRepository repo;

    public Product execute(Long id) {
        log.debug("Executing GetProductByIdUseCase with id: {}", id);
        return repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}