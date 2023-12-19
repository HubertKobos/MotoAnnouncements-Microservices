package com.motoannouncements.announcementsservice.controller;

import com.motoannouncements.announcementsservice.dto.*;
import com.motoannouncements.announcementsservice.entity.AnnouncementCommentEntity;
import com.motoannouncements.announcementsservice.exceptions.ImagesNotValidWithTypeException;
import com.motoannouncements.basedomains.dto.AnnouncementDto;
import com.motoannouncements.announcementsservice.entity.AnnouncementEntity;
import com.motoannouncements.announcementsservice.kafka.AnounncementProducer;
import com.motoannouncements.announcementsservice.service.AnnouncementService;
import com.motoannouncements.announcementsservice.service.S3BucketService;
import com.motoannouncements.basedomains.dto.AnnouncementEventDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementsController {
    private final AnnouncementService announcementService;
    private final S3BucketService s3BucketService;
    private final AnounncementProducer anounncementProducer;

    @PostMapping
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@RequestParam("file") List<MultipartFile> multipart, @Valid @RequestPart CreateAnnouncementRequest data) throws IOException {

        AnnouncementDto announcementDto = new AnnouncementDto();
        Boolean isImagesValid = announcementService.isCarBikeValidImages(multipart, data.getCategory());
        if(!isImagesValid){
            throw new ImagesNotValidWithTypeException("Images are not valid with given announcement type");
        }
        AnnouncementEntity createdDatabaseAnnouncement = announcementService.createAnnouncement(announcementDto, data);
        AnnouncementDto announcementDto1 = new ModelMapper().map(createdDatabaseAnnouncement, AnnouncementDto.class);
        List<InputStream> inputStreams = announcementService.mapMultipartFilesToInputStream(multipart);
        List<String> uploadedFilesKeys = s3BucketService.uploadFiles(data.getAuthorId(), inputStreams);

        // map file keys (names) to AnnouncementDto class
        announcementService.addAnnouncementDtoImagesFileKeys(announcementDto1, uploadedFilesKeys);

        // save announcements with added images
        AnnouncementEntity savedAnnouncement = announcementService.saveAnnouncement(announcementDto1);

        // create dto for kafka and send it to the broker
        AnnouncementEventDto announcementEventDto = new AnnouncementEventDto();
        announcementEventDto.setStatus("CREATED");
        announcementEventDto.setMessage("New announcement has been created");
        announcementEventDto.setAnnouncementDto(announcementDto1);
        anounncementProducer.sendMenssage(announcementEventDto);

        CreateAnnouncementResponse createAnnouncementResponse = new ModelMapper().map(savedAnnouncement, CreateAnnouncementResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAnnouncementResponse);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GetAnnouncementsResponse>> getAnnouncements(@PathVariable UUID userId){
        List<AnnouncementEntity> announcementEntityList = announcementService.getAnnouncements(userId);
        Type listType = new TypeToken<List<GetAnnouncementsResponse>>() {}.getType();
        List<GetAnnouncementsResponse> announcementsResponses = new ModelMapper().map(announcementEntityList, listType);
        return ResponseEntity.status(HttpStatus.OK).body(announcementsResponses);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<GetAnnouncementsResponse>> filterAnnouncements(@RequestBody AnnouncementFilterRequest filterRequest){
        List<AnnouncementEntity> announcementEntityList = announcementService.filterAnnouncements(filterRequest);
        Type listType = new TypeToken<List<GetAnnouncementsResponse>>() {}.getType();
        List<GetAnnouncementsResponse> announcementsResponses = new ModelMapper().map(announcementEntityList, listType);
        return ResponseEntity.status(HttpStatus.OK).body(announcementsResponses);
    }

    @GetMapping("/spec/{announcementId}")
    public ResponseEntity<Map<String, Object>> getAnnouncement(@PathVariable UUID announcementId, @RequestHeader("Authorization") String authorizationHeader){
        AnnouncementEntity announcement = announcementService.getAnnouncement(announcementId);

        UserResponseModel userResponseModel = announcementService.getUserData(announcement.getAuthorId(), authorizationHeader);

        // get unique ids from comments authors ids
        Set<UUID> commentsAuthorsIds = new HashSet<>();
        announcement.getComments().forEach(comment -> {
            UUID authorId = comment.getAuthorId();
            if (!commentsAuthorsIds.contains(authorId)) {
                commentsAuthorsIds.add(authorId);
            }
        });
        List<UUID> uniqueAuthorsIdsList = new ArrayList<>(commentsAuthorsIds);
        Map<UUID, UserResponseModel> commentsAuthorsInformations = announcementService.getUsersData(uniqueAuthorsIdsList, authorizationHeader);

        GetSpecAnnouncementWithComments announcementResponse = new ModelMapper().map(announcement, GetSpecAnnouncementWithComments.class);

        // map to get all the user information to put in the nested announcementResponse object
        announcementResponse.getComments().forEach(
                comment -> {
                    UUID authorId = comment.getAuthorId();
                    UserResponseModel userInformations = commentsAuthorsInformations.get(authorId);
                    comment.setUserInformations(userInformations);
                }
        );

        Map<String, Object> response = new HashMap<>();
        response.put("announcement", announcementResponse);
        response.put("user", userResponseModel);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/comment")
    public ResponseEntity<AnnouncementEntity> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        AnnouncementEntity announcementEntity = announcementService.getAnnouncement(createCommentRequest.getAnnouncementId());

        AnnouncementDto announcementDto = new ModelMapper().map(announcementEntity, AnnouncementDto.class);

        AnnouncementDto.AnnouncementCommentDto announcementCommentDto = new ModelMapper().map(createCommentRequest, AnnouncementDto.AnnouncementCommentDto.class);

        announcementCommentDto.setLikes(0);
        announcementCommentDto.setDislikes(0);
        announcementDto.getComments().add(announcementCommentDto);

        AnnouncementCommentEntity announcementCommentEntity = new ModelMapper().map(announcementCommentDto, AnnouncementCommentEntity.class);
        announcementEntity.getComments().add(announcementCommentEntity);

        AnnouncementEntity updatedAnnouncement = announcementService.saveAnnouncement(announcementEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedAnnouncement);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable UUID commentId){
//        TODO needs to be tested !
        announcementService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
