package com.motoannouncements.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserRequestModel {
    @Null
    private UUID id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
}
