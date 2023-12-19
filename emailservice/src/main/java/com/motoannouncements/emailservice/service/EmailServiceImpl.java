package com.motoannouncements.emailservice.service;

import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import com.motoannouncements.emailservice.dto.SaveUserToNewsletterRequest;
import com.motoannouncements.emailservice.dto.UserDto;
import com.motoannouncements.emailservice.entity.AnnouncementEntity;
import com.motoannouncements.emailservice.entity.BrandModelEntity;
import com.motoannouncements.emailservice.entity.UserEntity;
import com.motoannouncements.emailservice.enums.BrandType;
import com.motoannouncements.emailservice.exceptions.SaveAnnouncementException;
import com.motoannouncements.emailservice.exceptions.SaveUserException;
import com.motoannouncements.emailservice.repository.AnnouncementRepository;
import com.motoannouncements.emailservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private final JavaMailSender emailSender;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@moto.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    @Override
    public void saveUserToNewsletter(SaveUserToNewsletterRequest saveUserToNewsletterRequest) {

        /* This method saves new user but if the user exists it is getting update */
        Optional<UserEntity> userOptional = userRepository.findByUserId(saveUserToNewsletterRequest.getUserId());
        if(userOptional.isPresent()){
            List<BrandType> currentBrands = new ArrayList<>(userOptional.get().getBrandType());
            List<String> currentModels = new ArrayList<>(userOptional.get().getModel());

            UserEntity userToUpdate = addBrandModelToExistingUser(userOptional.get(), saveUserToNewsletterRequest.getBrand(), saveUserToNewsletterRequest.getModel());

            if(!currentBrands.equals(userToUpdate.getBrandType()) && !currentModels.equals(userToUpdate.getModel())){
                userRepository.save(userToUpdate);
            }
        }else{
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(saveUserToNewsletterRequest.getUserId());
            userEntity.setEmail(saveUserToNewsletterRequest.getEmail());
            if(saveUserToNewsletterRequest.getModel() != null){
                userEntity.setModel(List.of(saveUserToNewsletterRequest.getModel()));
            }else{
                userEntity.setModel(List.of("NONE"));
            }
            userEntity.setBrandType(List.of(saveUserToNewsletterRequest.getBrand()));
            try {
                userRepository.save(userEntity);
            } catch (Exception e) {
                throw new SaveUserException("The problem with creating instance occurred");
            }
        }
    }

    @Override
    public UserEntity addBrandModelToExistingUser(UserEntity user, BrandType brandType, String model) {
        for(int i = 0; i < user.getBrandType().size(); i++){
            if(user.getBrandType().get(i).equals(brandType) && user.getModel().get(i).equals(model)){
                return user;
            }else if(user.getBrandType().get(i).equals(brandType) && user.getModel().get(i).equals("NONE") && model.equals(model)){
                return user;
            }
        }
        user.getBrandType().add(brandType);
        user.getModel().add(model);
        return user;

    }

    @Override
    public void saveAnnouncement(AnnouncementEventDto announcementEventDto) {
        AnnouncementEntity announcementEntity = new ModelMapper().map(announcementEventDto.getAnnouncementDto(), AnnouncementEntity.class);
        announcementEntity.setId(null);
        announcementRepository.save(announcementEntity);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }
}
