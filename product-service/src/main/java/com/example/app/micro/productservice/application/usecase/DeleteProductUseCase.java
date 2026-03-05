package com.example.app.micro.productservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.productservice.domain.exception.ProductNotFoundException;
import com.example.app.micro.productservice.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteProductUseCase {
    private final ProductRepository repo;

    public void execute(Long id) {
        log.debug("Executing DeleteProductUseCase for productId: {}", id);

        if (!repo.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        repo.deleteById(id);

        log.info("Product deleted successfully with id {}", id);
    }


}
