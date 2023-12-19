package com.motoannouncements.announcementsservice.service;

import com.motoannouncements.announcementsservice.dto.AnnouncementFilterRequest;
import com.motoannouncements.announcementsservice.dto.UserResponseModel;
import com.motoannouncements.basedomains.dto.AnnouncementDto;
import com.motoannouncements.announcementsservice.dto.CreateAnnouncementRequest;
import com.motoannouncements.announcementsservice.entity.AnnouncementCommentEntity;
import com.motoannouncements.announcementsservice.entity.AnnouncementEntity;
import com.motoannouncements.announcementsservice.entity.S3BucketImageEntity;
import com.motoannouncements.enums.AnnouncementCategoryType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AnnouncementService {
    public AnnouncementDto addAnnouncementDtoImagesFileKeys(AnnouncementDto announcementDto, List<String> uploadedFilesKeys);
    public AnnouncementEntity saveAnnouncement(AnnouncementDto announcementDto);
    public AnnouncementEntity saveAnnouncement(AnnouncementEntity announcementEntity);
    public AnnouncementEntity createAnnouncement(AnnouncementDto announcementDto, CreateAnnouncementRequest createAnnouncementRequest);
    public List<InputStream> mapMultipartFilesToInputStream(List<MultipartFile> multipart) throws RuntimeException;
    public List<AnnouncementEntity> getAnnouncements(UUID userId);
    public UserResponseModel getUserData(UUID userId, String token);
    public Map<UUID, UserResponseModel> getUsersData(List<UUID> userIds, String token);
    public AnnouncementEntity getAnnouncement(UUID announcementId);
    public List<AnnouncementEntity> filterAnnouncements(AnnouncementFilterRequest filterRequest);
    public void addImage(S3BucketImageEntity image);
    public void removeImage(UUID image_id);
    public void addComment(AnnouncementCommentEntity comment);
    public void deleteComment(UUID comment_id);
    public boolean isCarBikeValidImages(List<MultipartFile> multipart, AnnouncementCategoryType announcementCategoryType);
}
