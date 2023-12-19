package com.motoannouncements.emailservice.dto;

import com.motoannouncements.emailservice.enums.BrandType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private UUID userId;
    private String email;
    private List<BrandModelDto> brandModelDto = new ArrayList<>();
    private LocalDateTime saveTime;
    private List<BrandType> brandType;
    private List<String> model;
    @Data
    public static class BrandModelDto{
        private UUID id;
        private BrandType brand;
        private String model;
    }
}
