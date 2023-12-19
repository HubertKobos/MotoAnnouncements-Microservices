package com.motoannouncements.inventoryservice.entity;

import com.motoannouncements.enums.AnnouncementCategoryType;
import com.motoannouncements.enums.BrandType;
import com.motoannouncements.enums.Condition;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RedisHash("Announcement")
@Data
public class AnnouncementEntity implements Serializable {
    private UUID id;
    private UUID authorId;
    private AnnouncementCategoryType category;
    private BrandType brand;
    private String model;
    private String description;
    private int yearOfProduction;
    private Condition condition;
    private BigDecimal price;
    private LocalDateTime creationTime;
    private List<S3BucketImageEntity> images = new ArrayList<>();
    private List<AnnouncementCommentEntity> comments = new ArrayList<>();
}
