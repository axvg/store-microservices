package com.example.app.micro.userservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.userservice.domain.exception.UserNotFoundException;
import com.example.app.micro.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteUserUseCase {
    private final UserRepository repo;

    public void execute(Long id) {
        log.debug("Executing DeleteUserUseCase with id: {}", id);

        if (!repo.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }

        repo.deleteById(id);
        log.info("User with id {} deleted successfully", id);
    }
}
