package com.motoannouncements.emailservice.service;

import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.emailservice.dto.SaveUserToNewsletterRequest;
import com.motoannouncements.emailservice.dto.UserDto;
import com.motoannouncements.emailservice.entity.UserEntity;
import com.motoannouncements.emailservice.enums.BrandType;

import java.util.List;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject, String text);

    public void saveUserToNewsletter(SaveUserToNewsletterRequest saveUserToNewsletterRequest);
    UserEntity addBrandModelToExistingUser(UserEntity user, BrandType brandType, String model);

    public void saveAnnouncement(AnnouncementEventDto announcementEventDto);
    public List<UserEntity> getAllUsers();
}
