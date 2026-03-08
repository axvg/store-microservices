package com.example.app.micro.orderservice.infrastructure.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.micro.orderservice.application.service.UserGateway;
import com.example.app.micro.orderservice.application.service.UserSnapshot;
import com.example.app.micro.orderservice.infrastructure.client.dto.UserClientDto;
import com.example.app.micro.orderservice.infrastructure.client.mapper.UserClientMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class UserClient implements UserGateway {
    private static final Logger log = LoggerFactory.getLogger(UserClient.class);
    private final RestTemplate restTemplate;
    private final UserClientMapper userClientMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserById")
    @Retry(name = "userService")
    public Optional<UserSnapshot> getUserById(Long userId) {
        try {
            ResponseEntity<UserClientDto> response = restTemplate.getForEntity(
                    userServiceUrl + "/api/users/" + userId,
                    UserClientDto.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return Optional.empty();
            }
            return Optional.of(userClientMapper.toSnapshot(response.getBody()));
        } catch (Exception ex) {
            log.error("Error getting user {}", userId, ex);
            return Optional.empty();
        }
    }

    public Optional<UserSnapshot> fallbackGetUserById(Long userId, Throwable t) {
        log.warn("Fallback triggered for getting user {} due to {}", userId, t.getMessage());
        return Optional.empty();
    }
}
