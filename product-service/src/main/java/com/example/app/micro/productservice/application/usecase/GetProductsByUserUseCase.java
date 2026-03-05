package com.example.app.micro.productservice.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

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
public class GetProductsByUserUseCase {
    private final ProductRepository repo;
    private final UserClient client;

    public List<Product> execute(Long userId) {
        User user = client.getUserById(userId);

        log.info("Fetching products for user from userdb: {}", user.getName());
        
        if (user == null) {
            log.warn("User with id {} not found in userdb", userId);
            throw new UserNotFoundException(userId);
        }

        log.debug("Executing GetProductsByUserUseCase with userId: {}", userId);
        return repo.findByCreatedBy(userId);
    }

}