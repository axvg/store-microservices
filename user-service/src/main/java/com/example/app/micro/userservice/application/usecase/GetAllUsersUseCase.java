package com.example.app.micro.userservice.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersUseCase {
    private final UserRepository repo;

    public List<User> execute() {
        log.debug("Executing GetAllUsersUseCase");
        return repo.findAll();
    }
}
