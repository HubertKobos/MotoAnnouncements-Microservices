package com.motoannouncements.basedomains.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementEventDto {
    private String message;
    private String status;
    private AnnouncementDto announcementDto;
}
