package com.motoannouncements.announcementsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponseModel {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] avatar;
}
