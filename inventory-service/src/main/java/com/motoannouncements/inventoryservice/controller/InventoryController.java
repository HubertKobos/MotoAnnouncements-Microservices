package com.motoannouncements.inventoryservice.controller;

import com.motoannouncements.inventoryservice.entity.AnnouncementEntity;
import com.motoannouncements.inventoryservice.service.AnnouncementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
@RequiredArgsConstructor
public class InventoryController {
    private final AnnouncementsService announcementsService;
    @GetMapping
    public ResponseEntity<List<AnnouncementEntity>> getAllAnnouncements(){
        List<AnnouncementEntity> announcementEntityList = announcementsService.getAllAnnouncements();
        return ResponseEntity.status(HttpStatus.OK).body(announcementEntityList);
    }
}
