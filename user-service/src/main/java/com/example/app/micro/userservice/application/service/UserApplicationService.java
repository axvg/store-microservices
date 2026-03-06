package com.example.app.micro.userservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.micro.userservice.application.usecase.CreateUserUseCase;
import com.example.app.micro.userservice.application.usecase.DeleteUserUseCase;
import com.example.app.micro.userservice.application.usecase.GetAllUsersUseCase;
import com.example.app.micro.userservice.application.usecase.GetUserByIdUseCase;
import com.example.app.micro.userservice.application.usecase.UpdateUserUseCase;
import com.example.app.micro.userservice.domain.model.User;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserApplicationService {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return getAllUsersUseCase.execute();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return getUserByIdUseCase.execute(id);
    }

    @Transactional
    public User createUser(User user) {
        return createUserUseCase.execute(user);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        return updateUserUseCase.execute(id, user);
    }

    @Transactional
    public void deleteUser(Long id) {
        deleteUserUseCase.execute(id);
    }
}