package com.motoannouncements.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

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

    public void uploadFile(UUID userId, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(userId.toString())
//                .acl("public-read")
                .contentType("image/png")
                .build();

        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
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