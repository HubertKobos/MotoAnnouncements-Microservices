package com.motoannouncements.announcementsservice.dto;

import com.motoannouncements.announcementsservice.enums.Country;
import com.motoannouncements.announcementsservice.enums.DriveType;
import com.motoannouncements.enums.FuelType;
import com.motoannouncements.announcementsservice.enums.GearboxType;
import com.motoannouncements.enums.AnnouncementCategoryType;
import com.motoannouncements.enums.BrandType;
import com.motoannouncements.enums.Condition;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementFilterRequest {
    private BrandType brand;
    private String model;
    private AnnouncementCategoryType category;
    private Condition condition;
    @Nullable
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private BigDecimal maxPrice;
    @Nullable
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private BigDecimal minPrice;
    @Nullable
    @Min(value = 1900, message = "Year of production must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year of production must be less than or equal to 2100")
    private Integer maxYearOfProduction;
    @Nullable
    @Min(value = 1900, message = "Year of production must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year of production must be less than or equal to 2100")
    private Integer minYearOfProduction;

    @Nullable
    private Boolean imported;
    @Nullable
    @Min(value = 1, message = "Cubic capacity must be greater than or equal to 1")
    private Integer cubicCapacityMin;
    @Nullable
    @Max(value = 10000, message = "Cubic capacity must be greater than or equal to 10000")
    private Integer cubicCapactiyMax;
    @Nullable
    private FuelType fuelType;
    @Nullable
    @Min(value = 1, message = "Power must be greater than or equal to 1")
    private Integer powerMin;
    @Nullable
    @Max(value = 10000, message = "Power must be less than or equal to 10000")
    private Integer powerMax;
    @Nullable
    private GearboxType gearboxType;
    @Nullable
    private DriveType driveType;
    @Nullable
    @Min(value = 1, message = "Number of doors must be greater than or equal to 1")
    private Integer numberOfDoorsMin;
    @Nullable
    @Max(value = 50, message = "Number of doors must be less than or equal to 50")
    private Integer numberOfDoorsMax;
    @Nullable
    @Min(value = 1, message = "Number of seats must be greater than or equal to 1")
    private Integer numberOfSeatsMin;
    @Nullable
    @Max(value = 50, message = "Number of seats must be greater than or equal to 50")
    private Integer numberOfSeatsMax;
    @Nullable
    private String colour;
    @Nullable
    private Country countryOfOrigin;
    @Nullable
    private Boolean accidentFree;
}
