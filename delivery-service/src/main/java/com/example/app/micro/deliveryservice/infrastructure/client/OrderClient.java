package com.example.app.micro.deliveryservice.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.micro.deliveryservice.application.service.OrderGateway;
import com.example.app.micro.deliveryservice.infrastructure.client.dto.OrderClientDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderClient implements OrderGateway {
    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Override
    public boolean existsById(Long orderId) {
        try {
            ResponseEntity<OrderClientDto> response = restTemplate.getForEntity(
                    orderServiceUrl + "/api/orders/internal/" + orderId,
                    OrderClientDto.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
