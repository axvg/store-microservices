package com.example.app.micro.orderservice.application.service;

import java.util.Optional;

public interface UserGateway {
    Optional<UserSnapshot> getUserById(Long userId);
}
