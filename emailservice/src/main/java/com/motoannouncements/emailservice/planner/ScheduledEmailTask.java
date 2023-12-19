package com.motoannouncements.emailservice.planner;

import com.motoannouncements.emailservice.entity.AnnouncementEntity;
import com.motoannouncements.emailservice.entity.UserEntity;
import com.motoannouncements.emailservice.repository.AnnouncementRepository;
import com.motoannouncements.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledEmailTask {
    private final EmailService emailService;
    private final AnnouncementRepository announcementRepository;
    @Scheduled(cron = "0 58 16 * * ?") // executes on sec/min/hour/dayOfTheMonth/Month/DayOfTheWeek
    public void sendScheduledEmails(){
        List<UserEntity> users = emailService.getAllUsers();
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        for(UserEntity user:users) {
            String text = new String("Here are new announcements related to your preferences." + '\n');
            List<AnnouncementEntity> announcementEntity = new ArrayList<>();
            for(int i = 0; i < user.getBrandType().size(); i++){
                if(user.getModel().get(i).equals("NONE")){
                    announcementRepository.findByBrandAndCreationTimeGreaterThan(user.getBrandType().get(i), twentyFourHoursAgo).stream().forEach(
                                    announcementEntity::add
                    );
                }else{
                    List<AnnouncementEntity> announcementsList= announcementRepository.findByBrandAndModelAndCreationTimeGreaterThan(user.getBrandType().get(i), user.getModel().get(i), twentyFourHoursAgo);
                    if(announcementsList.size() > 0){
                        announcementsList.stream().forEach(
                                announcementEntity::add
                        );
                    }
                }
            }

            for (AnnouncementEntity entity : announcementEntity) {
                text += "* " + entity.getBrand() + " " + entity.getModel() + " " + "http://motoann.com/" + entity.getAnnouncementId() + '\n';
            }
            String to = user.getEmail();
            String subject = "Newsletter";
            emailService.sendSimpleMessage(to, subject, text);
        }
    }

}
