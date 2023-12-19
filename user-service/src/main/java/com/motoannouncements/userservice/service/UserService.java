package com.motoannouncements.userservice.service;

import com.motoannouncements.userservice.dto.UpdateUserPasswordRequest;
import com.motoannouncements.userservice.dto.UpdateUserRequestModel;
import com.motoannouncements.userservice.dto.UserDto;
import com.motoannouncements.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserDto getUserByUserId(UUID userId);

    UserDto createUser(UserDto userDto);
    List<UserEntity> getUsersDetailsByKeys(List<UUID> userIds);
    List<UUID> convertUserIdsToUUID(List<String> userIds);
    UserDto getUserDetailsByEmail(String email);
    boolean isUserExistByEmail(String email);
    UserDto updateUser(UpdateUserRequestModel updateUserRequestModel);
    boolean changePassword(UUID userId, UpdateUserPasswordRequest updateUserPasswordRequest);
}
