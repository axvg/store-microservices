package com.example.app.micro.productservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.productservice.domain.exception.InvalidProductDataException;
import com.example.app.micro.productservice.domain.exception.ProductNotFoundException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateProductUseCase {
    private final ProductRepository repo;

    public Product execute(Long id, Product productDetails) {
        log.debug("Executing UpdateProductUseCase with id: {}", id);
        Product existingProduct = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (!productDetails.isValid()) {
            throw new InvalidProductDataException("Invalid product data. Name, valid price and stock are required.");
        }

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setCategory(productDetails.getCategory());

        Product updatedProduct = repo.save(existingProduct);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());

        return updatedProduct;
    }
}
