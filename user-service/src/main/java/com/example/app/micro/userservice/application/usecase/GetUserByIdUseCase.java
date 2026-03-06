package com.example.app.micro.userservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.userservice.domain.exception.UserNotFoundException;
import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUserByIdUseCase {
    private final UserRepository repo;

    public User execute(Long id) {
        log.debug("Executing GetUserByIdUseCase with id: {}", id);
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
