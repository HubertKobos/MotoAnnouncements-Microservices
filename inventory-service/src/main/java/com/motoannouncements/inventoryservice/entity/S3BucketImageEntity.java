package com.motoannouncements.inventoryservice.entity;

import lombok.Data;

import java.util.UUID;


@Data
public class S3BucketImageEntity {
    private UUID id;
    private String imageKey;

}
