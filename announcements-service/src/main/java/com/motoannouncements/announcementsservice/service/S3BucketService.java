package com.motoannouncements.announcementsservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


@Service
@Slf4j
public class S3BucketService {
    private final String bucketName;
    private final S3Client client;
    public S3BucketService(
            @Value("${aws.bucket.access.key}") String accessKey,
            @Value("${aws.bucket.secret.access.key}") String secretAccessKey,
            @Value("${aws.bucket.region}") Region region,
            @Value("${aws.bucket.name}") String bucketName){

        AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretAccessKey));

        S3Client client = S3Client.builder()
                .region(region)
                .credentialsProvider(awsCredentialsProvider)
                .build();
        this.bucketName = bucketName;
        this.client = client;
    }

    public List<String> uploadFiles(UUID userId, List<InputStream> inputStreams)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        List<String> keys = new ArrayList<>();
        for(int i = 0; i < inputStreams.size(); i++){
            InputStream inputStream = inputStreams.get(i);

            // Construct a unique key for each object
            String key = userId.toString() + UUID.randomUUID().toString();
            keys.add(key);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/png")
                    .build();

            client.putObject(request,
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
        }
        return keys;
    }
    public byte[] getPhotoData(UUID userId) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(userId.toString())
                .build();
        ResponseInputStream<GetObjectResponse> responseResponseInputStream = client.getObject(request);
        byte[] avatarData = responseResponseInputStream.readAllBytes();
        responseResponseInputStream.close();
        return avatarData;
    }
}