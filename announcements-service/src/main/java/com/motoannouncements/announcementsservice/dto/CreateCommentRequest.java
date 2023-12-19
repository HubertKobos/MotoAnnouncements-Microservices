package com.motoannouncements.announcementsservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @Null
    private UUID id;
    @NotNull
    private UUID authorId;
    @NotNull
    private UUID announcementId;
    @NotNull
    private String content;
}
