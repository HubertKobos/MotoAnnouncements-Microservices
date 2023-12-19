package com.motoannouncements.inventoryservice.repository;

import com.motoannouncements.inventoryservice.entity.AnnouncementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementsRepository extends CrudRepository<AnnouncementEntity, UUID> {
}
