package com.motoannouncements.announcementsservice.entity;

import com.motoannouncements.announcementsservice.enums.*;
import com.motoannouncements.enums.AnnouncementCategoryType;
import com.motoannouncements.enums.BrandType;
import com.motoannouncements.enums.FuelType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_announcement")
@Data
public class AnnouncementEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="author_id", nullable = false)
    private UUID authorId;

    @Column(name="description", nullable = false, length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="announcement_category_type", nullable = false)
    private AnnouncementCategoryType category;

    @Enumerated(EnumType.STRING)
    @Column(name="brand", nullable = false)
    private BrandType brand;

    @Column(name="model", nullable = true)
    private String model;

    @Column(name="price", nullable = false)
    private BigDecimal price;

    @Column(name="condition", nullable = false)
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name="year_of_production", nullable = false)
    private int yearOfProduction;

    @Column(name="imported", nullable = true)
    private boolean imported;
    @Column(name="cubic_capacity", nullable = false)
    private int cubicCapacity;
    @Column(name="fuel_type", nullable = false)
    private FuelType fuelType;
    @Column(name="power", nullable = false)
    private int power;
    @Column(name="gear_box", nullable = false)
    private GearboxType gearboxType;
    @Column(name="drive_type", nullable = false)
    private DriveType driveType;
    @Column(name="number_of_doors", nullable = false)
    private int numberOfDoors;
    @Column(name="number_of_seats", nullable = false)
    private int numberOfSeats;
    @Column(name="color", nullable = false)
    private String color;
    @Column(name="country_of_origin", nullable = false)
    private Country countryOfOrigin;
    @Column(name="accident_free", nullable = false)
    private boolean accidentFree;

    @Column(name="creation_time", nullable = false)
    private LocalDateTime creationTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "t_announcement_id")
    private List<S3BucketImageEntity> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "t_announcement_id")
    private List<AnnouncementCommentEntity> comments = new ArrayList<>();
}
