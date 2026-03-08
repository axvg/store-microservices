package com.example.app.micro.userservice.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.infrastructure.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

    List<User> toDomainList(List<UserEntity> entities);
}
