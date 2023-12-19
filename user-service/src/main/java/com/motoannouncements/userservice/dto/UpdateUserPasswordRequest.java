package com.motoannouncements.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPasswordRequest {
    @NotNull(message = "Password can not be null")
    @Size(min = 2, max = 50, message = "Password can not be less that 2 and more than 50 characters")
    private String oldPassword;
    @NotNull(message = "New password can not be null")
    @Size(min = 2, max = 50, message = "Password can not be less that 2 and more than 50 characters")
    private String newPassword;
    @NotNull(message = "New password can not be null")
    @Size(min = 2, max = 50, message = "Password can not be less that 2 and more than 50 characters")
    private String newPasswordConfirm;
}
