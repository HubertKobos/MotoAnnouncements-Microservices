package com.motoannouncements.announcementsservice.dto;

import com.motoannouncements.announcementsservice.entity.AnnouncementCommentEntity;
import com.motoannouncements.announcementsservice.entity.S3BucketImageEntity;
import com.motoannouncements.announcementsservice.enums.*;
import com.motoannouncements.basedomains.dto.AnnouncementDto;
import com.motoannouncements.enums.AnnouncementCategoryType;
import com.motoannouncements.enums.BrandType;
import com.motoannouncements.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GetSpecAnnouncementWithComments {
    private UUID id;

    private String description;

    private AnnouncementCategoryType category;

    private BrandType brand;

    private String model;

    private BigDecimal price;

    private Condition condition;

    private int yearOfProduction;

    private LocalDateTime creationTime;

    private boolean imported;
    private int cubicCapacity;
    private FuelType fuelType;
    private int power;
    private GearboxType gearboxType;
    private DriveType driveType;
    private int numberOfDoors;
    private int numberOfSeats;
    private String color;
    private Country countryOfOrigin;

    private boolean accidentFree;

    private List<S3BucketImageEntity> images = new ArrayList<>();
//    private List<AnnouncementCommentEntity> comments = new ArrayList<>();
    private List<AnnouncementCommentDto> comments = new ArrayList<>();
}
