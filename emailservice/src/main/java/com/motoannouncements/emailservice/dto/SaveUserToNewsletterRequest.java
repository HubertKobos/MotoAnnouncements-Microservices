package com.motoannouncements.emailservice.dto;

import com.motoannouncements.emailservice.enums.BrandType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserToNewsletterRequest {
    @NotNull(message = "User id can not be null")
    private UUID userId;
    @NotNull(message = "Email can not be null")
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Brand type can not be null")
    private BrandType brand;
    private String model;
}
