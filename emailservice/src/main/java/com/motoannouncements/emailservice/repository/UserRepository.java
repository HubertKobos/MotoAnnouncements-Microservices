package com.motoannouncements.emailservice.repository;

import com.motoannouncements.emailservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    public Optional<UserEntity> findByUserId(UUID userId);
}
