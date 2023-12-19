package com.motoannouncements.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestModel {
    @NotNull(message = "First Name can not be null")
    @Size(min=2, max = 50, message="First Name must not be less that two characters and greater than 50")
    private String firstName;
    @NotNull(message = "Last Name can not be null")
    @Size(min=2, max = 50, message="Last Name must not be less that two characters and greater than 50")
    private String lastName;
    @NotNull(message = "Email can not be null")
    @Size(min=2, max = 70, message="Last Name must not be less that two characters and greater than 70")
    @Email
    private String email;
    @NotNull(message = "Password can not be null")
    @Size(min=2, max = 50, message="Password must not be less that two characters and greater than 50")
    private String password;
}
