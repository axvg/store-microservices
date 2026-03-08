package com.example.app.micro.userservice.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.userservice.application.service.UserApplicationService;
import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaUserRepository;
import com.example.app.micro.userservice.infrastructure.security.UserPrincipal;
import com.example.app.micro.userservice.presentation.dto.CreateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UpdateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UserResponse;
import com.example.app.micro.userservice.presentation.mapper.UserDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserApplicationService userApplicationService;
    private final UserDtoMapper userDtoMapper;
    private final JpaUserRepository jpaUserRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("REST request to get all users");
        List<User> users = userApplicationService.getAllUsers();
        return ResponseEntity.ok(userDtoMapper.toResponseList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by id: {}", id);
        User user = userApplicationService.getUserById(id);
        return ResponseEntity.ok(userDtoMapper.toResponse(user));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        log.info("REST request to get current user: {}", authentication.getName());
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserEntity entity = jpaUserRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(
                UserResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .email(entity.getEmail())
                        .phone(entity.getPhone())
                        .address(entity.getAddress())
                        .createdAt(entity.getCreatedAt())
                        .updatedAt(entity.getUpdatedAt())
                        .build()
        );
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest request) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        log.info("REST request to update current user profile: {}", principal.getEmail());
        UserEntity entity = jpaUserRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        entity.setName(request.getName());
        entity.setPhone(request.getPhone());
        entity.setAddress(request.getAddress());
        UserEntity saved = jpaUserRepository.save(entity);
        return ResponseEntity.ok(
                UserResponse.builder()
                        .id(saved.getId())
                        .name(saved.getName())
                        .email(saved.getEmail())
                        .phone(saved.getPhone())
                        .address(saved.getAddress())
                        .createdAt(saved.getCreatedAt())
                        .updatedAt(saved.getUpdatedAt())
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("REST request to create user: {}", request.getEmail());
        User user = userDtoMapper.toDomain(request);
        User createdUser = userApplicationService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoMapper.toResponse(createdUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("REST request to update user with id: {}", id);
        User user = userDtoMapper.toDomain(request);
        User updatedUser = userApplicationService.updateUser(id, user);
        return ResponseEntity.ok(userDtoMapper.toResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("REST request to delete user with id: {}", id);
        userApplicationService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Service running with Clean Architecture!");
    }
}
