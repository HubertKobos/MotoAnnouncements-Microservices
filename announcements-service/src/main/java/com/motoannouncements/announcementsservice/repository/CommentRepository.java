package com.motoannouncements.announcementsservice.repository;

import com.motoannouncements.announcementsservice.entity.AnnouncementCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<AnnouncementCommentEntity, UUID> {
}
