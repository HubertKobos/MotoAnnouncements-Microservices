package com.motoannouncements.basedomains.dto;

import com.motoannouncements.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public class AnnouncementDto {
    private UUID id;
    private UUID authorId;
    private LocalDateTime creationTime;
    private String description;
    private AnnouncementCategoryType category;
    private BrandType brand;
    private String model;
    private Condition condition;
    private BigDecimal price;
    private int yearOfProduction;

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

    private List<S3BucketImageDto> images = new ArrayList<>();
    private List<AnnouncementCommentDto> comments = new ArrayList<>();
    public AnnouncementDto(){
        this.creationTime = LocalDateTime.now();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class S3BucketImageDto{
        private String imageKey;
        private UUID announcementId;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AnnouncementCommentDto{
        private UUID id;
        private UUID authorId;
        private String content;
        private UUID announcementId;
        private int likes;
        private int dislikes;
    }

}
