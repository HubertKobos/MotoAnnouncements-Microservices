package com.motoannouncements.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="t_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 70, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;
    @Column(name="avatar_url", nullable = true)
    private String avatarUrl;
}
