package com.motoannouncements.inventoryservice.kafka;

import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.inventoryservice.entity.AnnouncementEntity;
import com.motoannouncements.inventoryservice.repository.AnnouncementsRepository;
import com.motoannouncements.inventoryservice.service.AnnouncementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
public class AnnouncementsConsumer {
    private final AnnouncementsService announcementsService;
    public AnnouncementsConsumer(AnnouncementsService announcementsService){
        this.announcementsService = announcementsService;
    }
    @KafkaListener(topics= "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AnnouncementEventDto announcementEventDto){
        log.info(String.format("Announcement event received -> %s", announcementEventDto.toString()));

        // create (save) announcement in database
        announcementsService.createAnnouncement(announcementEventDto);
    }



}
