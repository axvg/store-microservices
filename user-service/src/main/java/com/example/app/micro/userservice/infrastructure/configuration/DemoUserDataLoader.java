package com.example.app.micro.userservice.infrastructure.configuration;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.app.micro.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DemoUserDataLoader {
    private final JpaUserRepository userRepository;
    private final JpaRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadDemoUsers() {
        return args -> {
            Map<String, String> users = Map.of(
                    "admin", "ROLE_ADMIN",
                    "operator", "ROLE_OPERATOR",
                    "courier1", "ROLE_DELIVERY",
                    "user1", "ROLE_USER");

            for (Map.Entry<String, String> entry : users.entrySet()) {
                String username = entry.getKey();
                String roleName = entry.getValue();
                String email = username + "@store.local";

                if (userRepository.findByEmail(email).isPresent()) {
                    continue;
                }

                RoleEntity role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalStateException("Role not found: " + roleName));

                UserEntity user = UserEntity.builder()
                        .name(username)
                        .email(email)
                        .phone("900000000")
                        .address("Demo Address")
                        .password(passwordEncoder.encode(username + "123"))
                        .enabled(true)
                        .roles(Set.of(role))
                        .build();

                userRepository.save(user);
            }
        };
    }
}
