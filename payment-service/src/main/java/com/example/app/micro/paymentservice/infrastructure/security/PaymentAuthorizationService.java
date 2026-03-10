package com.example.app.micro.paymentservice.infrastructure.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.micro.paymentservice.infrastructure.client.dto.OrderClientDto;

import lombok.RequiredArgsConstructor;

@Component("paymentAuthorizationService")
@RequiredArgsConstructor
public class PaymentAuthorizationService {
    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public boolean canAccessOrder(Long orderId, Authentication authentication) {
        if (isPrivileged(authentication)) {
            return true;
        }
        Long principalUserId = extractUserId(authentication);
        if (principalUserId == null || orderId == null) {
            return false;
        }

        try {
            OrderClientDto order = restTemplate.getForObject(
                    orderServiceUrl + "/api/orders/internal/" + orderId,
                    OrderClientDto.class);
            return order != null && principalUserId.equals(order.getUserId());
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean isPrivileged(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ROLE_OPERATOR".equals(a.getAuthority()));
    }

    private Long extractUserId(Authentication authentication) {
        Object details = authentication.getDetails();
        if (details instanceof Map<?, ?> map) {
            Object value = map.get("userId");
            if (value instanceof Number number) {
                return number.longValue();
            }
        }
        return null;
    }
}
