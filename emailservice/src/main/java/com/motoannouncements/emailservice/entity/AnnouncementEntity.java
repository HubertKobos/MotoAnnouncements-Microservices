package com.motoannouncements.emailservice.entity;

import com.motoannouncements.emailservice.enums.BrandType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="announcement")
@Data
public class AnnouncementEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name="announcement_id", nullable = false)
    private UUID announcementId;
    @Enumerated(EnumType.STRING)
    @Column(name="brand", nullable = false)
    private BrandType brand;
    @Column(name="model")
    private String model;


    @Column(name="creation_time", nullable = false)
    private LocalDateTime creationTime;
}
