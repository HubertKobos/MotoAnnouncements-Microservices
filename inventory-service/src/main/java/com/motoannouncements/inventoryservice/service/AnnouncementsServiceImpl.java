package com.motoannouncements.inventoryservice.service;

import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.inventoryservice.entity.AnnouncementEntity;
import com.motoannouncements.inventoryservice.repository.AnnouncementsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnnouncementsServiceImpl implements AnnouncementsService {
    private final AnnouncementsRepository announcementsRepository;

    public AnnouncementsServiceImpl(AnnouncementsRepository announcementsRepository) {
        this.announcementsRepository = announcementsRepository;
    }

    @Override
    public void createAnnouncement(AnnouncementEventDto announcementEventDto) {
        AnnouncementEntity announcementEntity = new ModelMapper().map(announcementEventDto.getAnnouncementDto(), AnnouncementEntity.class);
        log.info(announcementEntity.toString());
        // save received announcement to database
        announcementsRepository.save(announcementEntity);
    }

    @Override
    public List<AnnouncementEntity> getAllAnnouncements() {
        Iterable<AnnouncementEntity> annoncementsList = announcementsRepository.findAll();
        List<AnnouncementEntity> announcementEntityList = new ArrayList<>();
        annoncementsList.forEach(announcement -> announcementEntityList.add(announcement));
        return announcementEntityList;
    }
}
