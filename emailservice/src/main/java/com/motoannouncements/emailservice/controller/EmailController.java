package com.motoannouncements.emailservice.controller;

import com.motoannouncements.emailservice.dto.SaveUserToNewsletterRequest;
import com.motoannouncements.emailservice.dto.UserDto;
import com.motoannouncements.emailservice.entity.UserEntity;
import com.motoannouncements.emailservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;
    @GetMapping
    public void sendMessage(){
        emailService.sendSimpleMessage("email@email.com", "subject", "text");
    }
    @PostMapping("/newsletter")
    public ResponseEntity<Void> saveUserToNewsletter(@Valid @RequestBody SaveUserToNewsletterRequest saveUserToNewsletterRequest){
        if(saveUserToNewsletterRequest.getModel() == "" || saveUserToNewsletterRequest.getModel() == null){
            saveUserToNewsletterRequest.setModel("NONE");
        }

        emailService.saveUserToNewsletter(saveUserToNewsletterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
