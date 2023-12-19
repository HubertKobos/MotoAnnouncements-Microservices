package com.motoannouncements.inventoryservice.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class AnnouncementCommentEntity {
    private UUID id;
    private UUID authorId;
    private String content;
    private LocalDateTime creationtime;
}
