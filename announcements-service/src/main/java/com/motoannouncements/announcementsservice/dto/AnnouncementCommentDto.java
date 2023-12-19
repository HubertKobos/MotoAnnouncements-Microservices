package com.motoannouncements.announcementsservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementCommentDto {

    private UUID id;


    private UUID authorId;


    private UUID announcementId;


    private String content;


    private int likes;

    private int dislikes;
    private UserResponseModel userInformations;

    private LocalDateTime creationtime;

}
