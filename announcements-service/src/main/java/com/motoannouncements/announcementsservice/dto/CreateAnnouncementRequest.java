package com.motoannouncements.announcementsservice.dto;

import com.motoannouncements.enums.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateAnnouncementRequest {
    @NotNull(message = "Author id can not be null")
    private UUID authorId;

    @NotNull(message = "Description can not be null")
    @Size(min=2, max=1024)
    private String description;

    @NotNull(message = "Category has to be provided")
    @Enumerated(EnumType.STRING)
    private AnnouncementCategoryType category;

    @NotNull(message = "Brand can not be null")
    @Enumerated(EnumType.STRING)
    private BrandType brand;

    @NotNull(message = "Condition can not be null")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @NotNull(message = "Price can not be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private BigDecimal price;

    @NotNull(message = "Year of production can not be null")
    @Min(value = 1900, message = "Year of production must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year of production must be less than or equal to 2100")
    private int yearOfProduction;

    @Size(min=2, max=1024)
    private String model;
    private boolean imported;

    @NotNull(message = "Cubic capacity can not be null")
    @Min(value = 1, message = "Cubic capacity must be greater than or equal to 1")
    private int cubicCapacity;

    @NotNull(message = "Fuel type can not be null")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @NotNull(message = "Power value can not be null")
    @Min(value = 1, message = "Power must be greater than or equal to 1")
    private int power;

    @NotNull(message = "Gear box can not be null")
    @Enumerated(EnumType.STRING)
    private GearboxType gearboxType;

    @NotNull(message = "Drive type can not be null")
    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @NotNull(message = "Number of doors can not be null")
    @Min(value = 1, message = "Number of doors must be greater than or equal to 1")
    private int numberOfDoors;

    @NotNull(message = "Number of seats can not be null")
    @Min(value = 1, message = "Number of seats must be greater than or equal to 1")
    private int numberOfSeats;

    @NotNull(message = "Color can not be null")
    @Size(min=2, max=512)
    private String color;

    @NotNull(message = "Country of origin can not be null")
    @Enumerated(EnumType.STRING)
    private Country countryOfOrigin;

    @NotNull(message = "Accident free can not be null")
    private boolean accidentFree;
}
