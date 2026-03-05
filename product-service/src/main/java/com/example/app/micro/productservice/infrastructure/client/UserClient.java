package com.example.app.micro.productservice.infrastructure.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.infrastructure.client.dto.UserDto;
import com.example.app.micro.productservice.infrastructure.client.mapper.UserDtoMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {
    private final RestTemplate restTemplate;
    private final UserDtoMapper userDtoMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public User getUserById(Long userId, String jwtToken) {
        log.info("Fetching user with id {} from userdb", userId);
        String url = this.userServiceUrl + "/api/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("JWT token is missing or empty");
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
    
        try {
            ResponseEntity<UserDto> response  = restTemplate.exchange(
                url, HttpMethod.GET, entity, UserDto.class
            );
            log.info("Received response for user id {}: {}", userId, response.getBody());
            return userDtoMapper.toDomain(response.getBody());
        } catch (Exception e) {
            log.error("Error calling User Service for user id {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error calling User Service", e);
        }
    }

    public User getUserById(Long userId) {
        return getUserById(userId, null);
    }


    public User getUserFallback(Long userId, String jwtToken, Throwable throwable) {
        log.warn("FALLBACK: User Service no disponible para userId: {}. Razón: {}",
                userId, throwable.getMessage());

        return User.builder()
                .id(userId)
                .name("Usuario no disponible")
                .email("N/A")
                .build();
    }
}
