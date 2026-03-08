package com.example.app.micro.productservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.micro.productservice.application.usecase.CreateProductUseCase;
import com.example.app.micro.productservice.application.usecase.DeleteProductUseCase;
import com.example.app.micro.productservice.application.usecase.GetAllProductsUseCase;
import com.example.app.micro.productservice.application.usecase.GetAvailableProductsUseCase;
import com.example.app.micro.productservice.application.usecase.GetProductByIdUseCase;
import com.example.app.micro.productservice.application.usecase.GetProductsByUserUseCase;
import com.example.app.micro.productservice.application.usecase.UpdateProductUseCase;
import com.example.app.micro.productservice.domain.model.Product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductApplicationService {
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAvailableProductsUseCase getAvailableProductsUseCase;
    private final GetProductsByUserUseCase getProductsByUserUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;


    @Transactional
    public List<Product> getAllProducts() {
        return getAllProductsUseCase.execute();
    }

    @Transactional
    public Product getProductById(Long id) {
        return getProductByIdUseCase.execute(id);
    }

    @Transactional
    public List<Product> getAvailableProducts() {
        return getAvailableProductsUseCase.execute();
    }

    @Transactional
    public List<Product> getProductsByUser(Long userId) {
        return getProductsByUserUseCase.execute(userId);
    }

    @Transactional
    public Product createProduct(Product product) {
        return createProductUseCase.execute(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        return updateProductUseCase.execute(id, product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        deleteProductUseCase.execute(id);
    }

}
 