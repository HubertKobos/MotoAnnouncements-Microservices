package com.motoannouncements.announcementsservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="t_images")
@Data
public class S3BucketImageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="image_url", nullable = false)
    private String imageKey;

}
