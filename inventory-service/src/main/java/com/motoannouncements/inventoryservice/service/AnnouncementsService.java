package com.motoannouncements.inventoryservice.service;

import com.motoannouncements.basedomains.dto.AnnouncementDto;
import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.inventoryservice.entity.AnnouncementEntity;

import java.util.List;

public interface AnnouncementsService {
    public void createAnnouncement(AnnouncementEventDto announcementEventDto);
    public List<AnnouncementEntity> getAllAnnouncements();
}
