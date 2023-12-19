package com.motoannouncements.announcementsservice.repository;

import com.motoannouncements.announcementsservice.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID>, JpaSpecificationExecutor<AnnouncementEntity> {
    public List<AnnouncementEntity> findByAuthorId(UUID userId);
    public Optional<AnnouncementEntity> findById(UUID announcementId);
}
