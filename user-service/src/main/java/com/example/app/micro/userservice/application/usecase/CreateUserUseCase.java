package com.example.app.micro.userservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.userservice.domain.exception.DuplicateEmailException;
import com.example.app.micro.userservice.domain.exception.InvalidUserDataException;
import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCase {
    private final UserRepository repo;

    public User execute(User user) {
        log.debug("Executing CreateUserUseCase with email: {}", user.getEmail());

        if (!user.isValid()) {
            throw new InvalidUserDataException("Invalid user data. Valid name and email are required");
        }

        if (repo.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(user.getEmail());
        }

        User savedUser = repo.save(user);
        log.info("User created with id: {}", savedUser.getId());

        return savedUser;
    }
}
