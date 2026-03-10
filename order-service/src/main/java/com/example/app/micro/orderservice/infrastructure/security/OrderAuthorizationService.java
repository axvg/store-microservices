package com.example.app.micro.orderservice.infrastructure.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.app.micro.orderservice.application.service.OrderApplicationService;
import com.example.app.micro.orderservice.domain.model.Order;

import lombok.RequiredArgsConstructor;

@Component("orderAuthorizationService")
@RequiredArgsConstructor
public class OrderAuthorizationService {
    private final OrderApplicationService orderApplicationService;

    public boolean canAccessUser(Long userId, Authentication authentication) {
        if (isPrivileged(authentication)) {
            return true;
        }
        Long principalUserId = extractUserId(authentication);
        return principalUserId != null && principalUserId.equals(userId);
    }

    public boolean canAccessOrder(Long orderId, Authentication authentication) {
        if (isPrivileged(authentication)) {
            return true;
        }
        Long principalUserId = extractUserId(authentication);
        if (principalUserId == null) {
            return false;
        }

        Order order = orderApplicationService.getOrderById(orderId);
        return principalUserId.equals(order.getUserId());
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
