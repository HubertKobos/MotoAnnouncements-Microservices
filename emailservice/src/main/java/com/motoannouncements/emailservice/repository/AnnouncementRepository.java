package com.motoannouncements.emailservice.repository;

import com.motoannouncements.emailservice.entity.AnnouncementEntity;
import com.motoannouncements.emailservice.enums.BrandType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {
    List<AnnouncementEntity> findByBrandInAndModelInAndCreationTimeGreaterThan(List<BrandType> brand, List<String> model, LocalDateTime creationTime);

    List<AnnouncementEntity> findByBrandAndModelAndCreationTimeGreaterThan(BrandType brandType, String s, LocalDateTime twentyFourHoursAgo);

    List<AnnouncementEntity> findByBrandAndCreationTimeGreaterThan(BrandType brandType, LocalDateTime twentyFourHoursAgo);
}
