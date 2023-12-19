package com.motoannouncements.emailservice.entity;

import com.motoannouncements.emailservice.enums.BrandType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="t_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private List<BrandType> brandType;
    private List<String> model;
    @Email
    @Column(name="email", nullable = false)
    private String email;

    @Column(name="save_time", nullable = false)
    private LocalDateTime saveTime;

    @PrePersist
    private void prePersist(){
        this.saveTime = LocalDateTime.now();
    }
}
