package com.example.app.micro.productservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.productservice.domain.exception.ProductNotFoundException;
import com.example.app.micro.productservice.domain.exception.UserNotFoundException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import com.example.app.micro.productservice.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdUseCase {
    private final ProductRepository repo;
    private final UserClient client;

    public Product execute(Long id, String jwtToken) {
        log.debug("Executing GetProductByIdUseCase with id: {}", id);

        Product product = repo.findById(id).orElseThrow(
            () -> new ProductNotFoundException(id)
        );


        User user = client.getUserById(product.getCreatedBy(), jwtToken);
        log.info("Fetching user from userdb: {}", user);
    
        if (user == null) {
            log.warn("User with id {} not found in userdb", product.getCreatedBy());
            throw new UserNotFoundException(id);
        }
        product.setCreatedByUser(user);
        return product;
    }
}