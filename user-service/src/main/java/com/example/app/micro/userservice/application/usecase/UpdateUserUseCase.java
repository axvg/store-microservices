package com.example.app.micro.userservice.application.usecase;

import org.springframework.stereotype.Component;

import com.example.app.micro.userservice.domain.exception.DuplicateEmailException;
import com.example.app.micro.userservice.domain.exception.UserNotFoundException;
import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateUserUseCase {
    private final UserRepository repo;

    public User execute(Long id, User userDetails){
        log.debug("Executing UpdateUserUseCase with id: {}", id);

        User existingUser = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!userDetails.isValid()) {
            throw new IllegalArgumentException("Invalid user data. Valid name and email are required");
        }

        if (!existingUser.getEmail().equals(userDetails.getEmail())) {
            if (repo.existsByEmail(userDetails.getEmail())){
                throw new DuplicateEmailException(userDetails.getEmail());
            }
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setAddress(userDetails.getAddress());

        User updatedUser = repo.save(existingUser);
        log.info("User with id {} updated successfully", updatedUser.getId());

        return updatedUser;
    }
}
