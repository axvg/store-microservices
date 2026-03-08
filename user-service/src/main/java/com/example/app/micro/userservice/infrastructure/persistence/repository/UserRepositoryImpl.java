package com.example.app.micro.userservice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;
import com.example.app.micro.userservice.infrastructure.persistence.mapper.UserPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository{
    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;
    
    @Override
    public List<User> findAll() {
        log.debug("Finding all users");
        return userPersistenceMapper.toDomainList(jpaUserRepository.findAll());
    }
    
    @Override
    public Optional<User> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return jpaUserRepository.findById(id).map(userPersistenceMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return jpaUserRepository.findByEmail(email).map(userPersistenceMapper::toDomain);
    }
    
    @Override
    public User save(User user) {
        log.debug("Saving user: {}", user.getEmail());
        return userPersistenceMapper.toDomain(
            jpaUserRepository.save(userPersistenceMapper.toEntity(user)));
    }
    
    @Override
    public void deleteById(Long id) {
        log.debug("Deleting user by id: {}", id);
        jpaUserRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking if email exists: {}", email);
        return jpaUserRepository.existsByEmail(email);
    }
}
