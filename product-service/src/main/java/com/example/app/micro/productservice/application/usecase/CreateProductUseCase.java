package com.example.app.micro.productservice.application.usecase;

import com.example.app.micro.productservice.domain.exception.InvalidProductDataException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProductUseCase {

    private final ProductRepository repo;

    public Product execute(Product product) {
        log.debug(
            "Executing CreateProductUseCase for product: {}",
            product.getName()
        );

        if (!product.isValid()) {
            throw new InvalidProductDataException("Product data is invalid");
        }

        Product savedProduct = repo.save(product);

        log.info("Product created with id: {}", savedProduct.getId());

        return savedProduct;
    }
}
