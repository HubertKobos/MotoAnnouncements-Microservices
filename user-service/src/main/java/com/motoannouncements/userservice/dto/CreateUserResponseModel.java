package com.motoannouncements.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserResponseModel {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
