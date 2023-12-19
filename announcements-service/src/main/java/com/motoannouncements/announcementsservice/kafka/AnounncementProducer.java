package com.motoannouncements.announcementsservice.kafka;


import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnounncementProducer {
    private NewTopic topic;
    private KafkaTemplate<String, AnnouncementEventDto> kafkaTemplate;
    public AnounncementProducer(NewTopic topic, KafkaTemplate<String, AnnouncementEventDto> kafkaTemplate){
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMenssage(AnnouncementEventDto event){
        log.info(String.format("Announcement event -> %s", event.toString()));

        // create message
        Message<AnnouncementEventDto> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
