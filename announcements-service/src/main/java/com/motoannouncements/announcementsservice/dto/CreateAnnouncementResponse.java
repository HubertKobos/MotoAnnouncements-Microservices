package com.motoannouncements.announcementsservice.dto;

import com.motoannouncements.announcementsservice.entity.S3BucketImageEntity;
import com.motoannouncements.announcementsservice.enums.Country;
import com.motoannouncements.announcementsservice.enums.DriveType;
import com.motoannouncements.enums.FuelType;
import com.motoannouncements.announcementsservice.enums.GearboxType;
import com.motoannouncements.enums.AnnouncementCategoryType;
import com.motoannouncements.enums.BrandType;
import com.motoannouncements.enums.Condition;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateAnnouncementResponse {
    private UUID id;
    private int price;
    private int yearOfProduction;
    private Condition condition;
    private String description;
    private AnnouncementCategoryType category;
    private BrandType brand;
    private String model;

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
    private List<S3BucketImageEntity> images;
}
