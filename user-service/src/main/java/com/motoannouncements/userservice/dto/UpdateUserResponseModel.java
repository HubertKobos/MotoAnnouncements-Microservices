package com.motoannouncements.userservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserResponseModel {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
