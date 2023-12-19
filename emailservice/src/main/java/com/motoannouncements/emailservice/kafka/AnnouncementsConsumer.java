package com.motoannouncements.emailservice.kafka;

import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnnouncementsConsumer {
    private final EmailService emailService;
    public AnnouncementsConsumer(EmailService emailService){
        this.emailService = emailService;
    }
    @KafkaListener(topics= "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AnnouncementEventDto announcementEventDto){
        log.info(String.format("Announcement event received -> %s", announcementEventDto.toString()));

//         create (save) announcement in database
        emailService.saveAnnouncement(announcementEventDto);
    }



}

