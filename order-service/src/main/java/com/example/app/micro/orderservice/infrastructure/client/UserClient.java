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

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClient implements UserGateway {
    private final RestTemplate restTemplate;
    private final UserClientMapper userClientMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Override
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
            return Optional.empty();
        }
    }
}
