package com.motoannouncements.userservice.controller;

import com.motoannouncements.userservice.dto.*;
import com.motoannouncements.userservice.entity.UserEntity;
import com.motoannouncements.userservice.exceptions.AwsS3BucketException;
import com.motoannouncements.userservice.exceptions.UserAlreadyExistsException;
import com.motoannouncements.userservice.exceptions.UserNotFoundException;
import com.motoannouncements.userservice.service.S3BucketService;
import com.motoannouncements.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    // TODO: send urls instead of byte[] as return of images

    private final S3BucketService s3BucketService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userRequestModel){
        UserDto userDto = new ModelMapper().map(userRequestModel, UserDto.class);
        if(userService.isUserExistByEmail(userRequestModel.getEmail())){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", userRequestModel.getEmail()));
        }
        UserDto createdUser = userService.createUser(userDto);
        CreateUserResponseModel userResponseModel = new ModelMapper().map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable UUID userId){
        byte[] avatar = new byte[0];
        UserDto userDto = userService.getUserByUserId(userId);
        try {
            // fetching from aws from backend only in this controller, normally avatar is fetched in the client code
            avatar = s3BucketService.getPhotoData(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchKeyException e){
            if(e.statusCode() == 404){
                userDto.setAvatar(null);
            }
            else throw new RuntimeException(e);
        }
        userDto.setAvatar(avatar);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
    }

    @PostMapping("/usersdata")
    public ResponseEntity<List<UserResponseModel>> getUsersByIds(@RequestBody List<UUID> userIds){
        log.info("send ids --> " + userIds);
        List<UserEntity> users = userService.getUsersDetailsByKeys(userIds);
        ModelMapper modelMapper = new ModelMapper();
        List<UserResponseModel> userResponseModels = users.stream().map(userEntity -> modelMapper.map(userEntity, UserResponseModel.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(userResponseModels);
    }

    @PostMapping("/{userId}/upload")
    public ResponseEntity<?> handleUploadForm(Model model, String description,
                                   @RequestParam("file") MultipartFile multipart,
                                           @PathVariable UUID userId) {
        try {
            s3BucketService.uploadFile(userId, multipart.getInputStream());
        } catch (Exception ex) {
            throw new AwsS3BucketException("Error uploading file to S3: " + ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{userId}/avatar")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable UUID userId){
        byte[] avatarData;
        try {
            avatarData = s3BucketService.getPhotoData(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(avatarData);
    }

    @PostMapping("/{userId}/update")
    public ResponseEntity<UpdateUserResponseModel> updateUser(@Valid @RequestBody UpdateUserRequestModel updateUserRequestModel,
                                                              @PathVariable UUID userId){
        updateUserRequestModel.setId(userId);
        UserDto userDto = userService.updateUser(updateUserRequestModel);
        UpdateUserResponseModel updateUserResponseModel = new ModelMapper().map(userDto, UpdateUserResponseModel.class);
        return ResponseEntity.ok(updateUserResponseModel);
    }

    @PostMapping("/{userId}/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest, @PathVariable UUID userId){
        boolean success = userService.changePassword(userId, updateUserPasswordRequest);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password");
        }
    }
}
