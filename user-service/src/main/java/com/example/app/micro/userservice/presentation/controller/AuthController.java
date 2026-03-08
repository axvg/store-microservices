package com.example.app.micro.userservice.presentation.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.micro.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaUserRepository;
import com.example.app.micro.userservice.infrastructure.configuration.JwtTokenProvider;
import com.example.app.micro.userservice.infrastructure.security.CustomUserDetailsService;
import com.example.app.micro.userservice.presentation.dto.LoginRequest;
import com.example.app.micro.userservice.presentation.dto.LoginResponse;
import com.example.app.micro.userservice.presentation.dto.RegisterRequest;
import com.example.app.micro.userservice.presentation.dto.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Intento de registro para: {}", request.getEmail());

        if (jpaUserRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Email duplicado",
                    "status", 409,
                    "message", "Ya existe una cuenta con ese email"
            ));
        }

        RoleEntity userRole = jpaRoleRepository.findByName("ROLE_USER")
                .orElseGet(() -> jpaRoleRepository.save(RoleEntity.builder()
                        .name("ROLE_USER")
                        .description("Standard user role")
                        .build()));

        UserEntity userToSave = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        UserEntity saved = jpaUserRepository.save(userToSave);

        RegisterResponse response = RegisterResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .address(saved.getAddress())
                .enabled(Boolean.TRUE.equals(saved.getEnabled()))
                .build();

        log.info("Registro exitoso para: {}", saved.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        log.info("Intento de login para: {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

            String token = jwtTokenProvider.generateToken(userDetails);

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .email(request.getEmail())
                    .roles(userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build();

            log.info("Login exitoso para: {}", request.getEmail());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            log.warn("Credenciales inválidas para: {}", request.getEmail());
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Credenciales inválidas",
                    "status", 401,
                    "message", "Email o password incorrectos"
            ));
        }
    }
}
