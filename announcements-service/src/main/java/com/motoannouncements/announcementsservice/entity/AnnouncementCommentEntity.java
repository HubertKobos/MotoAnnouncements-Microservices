package com.motoannouncements.announcementsservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_announcement_comments")
@Data
public class AnnouncementCommentEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name="announcementId", nullable = false)
    private UUID announcementId;

    @Column(name = "content", nullable = false, length = 1024)
    private String content;

    @Column(name = "likes", columnDefinition = "int default 0")
    private int likes;
    @Column(name = "dislikes", columnDefinition = "int default 0")
    private int dislikes;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationtime;

    @PrePersist
    public void prePersist(){
        this.creationtime = LocalDateTime.now();
    }
}
