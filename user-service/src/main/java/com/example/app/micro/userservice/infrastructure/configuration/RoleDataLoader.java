package com.example.app.micro.userservice.infrastructure.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.app.micro.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.app.micro.userservice.infrastructure.persistence.repository.JpaRoleRepository;

import java.util.List;

@Configuration
public class RoleDataLoader {

    @Bean
    public CommandLineRunner loadRoles(JpaRoleRepository roleRepository) {
        return args -> {
            List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_OPERATOR", "ROLE_DELIVERY", "ROLE_RESTAURANT");
            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(RoleEntity.builder()
                            .name(roleName)
                            .description(roleName.replace("ROLE_", "") + " role")
                            .build());
                }
            }
        };
    }
}
