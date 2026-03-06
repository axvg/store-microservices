package com.example.app.micro.userservice.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.presentation.dto.CreateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UpdateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UserResponse;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    User toDomain(CreateUserRequest request);

    User toDomain(UpdateUserRequest request);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
