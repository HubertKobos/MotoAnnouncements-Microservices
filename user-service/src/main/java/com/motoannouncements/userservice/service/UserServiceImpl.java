package com.motoannouncements.userservice.service;

import com.motoannouncements.userservice.dto.UpdateUserPasswordRequest;
import com.motoannouncements.userservice.dto.UpdateUserRequestModel;
import com.motoannouncements.userservice.dto.UserDto;
import com.motoannouncements.userservice.entity.UserEntity;
import com.motoannouncements.userservice.exceptions.MismatchedPasswordException;
import com.motoannouncements.userservice.exceptions.UserNotFoundException;
import com.motoannouncements.userservice.exceptions.UserUpdateException;
import com.motoannouncements.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if(userEntity.isPresent()){
            UserEntity user= userEntity.get();
            return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public UserDto getUserByUserId(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isPresent()){
            UserDto userDto = new ModelMapper().map(userEntity.get(), UserDto.class);
            return userDto;
        }else{
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);
        UserEntity newCreatedUser = userRepository.save(userEntity);
        userDto.setId(newCreatedUser.getId());
        return userDto;
    }

    @Override
    public List<UserEntity> getUsersDetailsByKeys(List<UUID> userIds) {
//        List<UUID> convertedIds = convertUserIdsToUUID(userIds);
//        log.info("converted userids --> " + convertedIds);
        List<UserEntity> users = userRepository.findByIdIn(userIds);
        return users;
    }

    @Override
    public List<UUID> convertUserIdsToUUID(List<String> userIds) {
        List<UUID> uuids = userIds.stream().map(id -> UUID.fromString(id)).collect(Collectors.toList());
        return uuids;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        Optional<UserEntity> userDetails = userRepository.findByEmail(email);
        if(userDetails.isPresent()){
            UserDto userDto = new ModelMapper().map(userDetails.get(), UserDto.class);
            return userDto;
        }else{
            throw new UserNotFoundException(String.format("User with email %s not found", email));
        }
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return true;
        }else{
            return false;
        }


    }

    @Override
    public UserDto updateUser(UpdateUserRequestModel updateUserRequestModel) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(updateUserRequestModel.getId());
        UserEntity user;
        if (userEntityOptional.isPresent()) {
            user = userEntityOptional.get();
            if (StringUtils.isNotBlank(updateUserRequestModel.getFirstName()) && updateUserRequestModel.getFirstName() != null) {
                user.setFirstName(updateUserRequestModel.getFirstName());
            }
            if(StringUtils.isNotBlank(updateUserRequestModel.getLastName()) && updateUserRequestModel.getLastName() != null) {
                user.setLastName(updateUserRequestModel.getLastName());
            }
            if(StringUtils.isNotBlank(updateUserRequestModel.getEmail()) && updateUserRequestModel.getEmail() != null){
                user.setEmail(updateUserRequestModel.getEmail());
            }
        } else {
            throw new UserNotFoundException(String.format("User with id %s not found", updateUserRequestModel.getId().toString()));
        }
        UserEntity savedUser;
        try {
            savedUser = userRepository.save(user);
        }catch(DataAccessException ex){
            throw new UserUpdateException(String.format("Failed to update user with id %s ", updateUserRequestModel.getId().toString()) + ". Additional message: " + ex.getMessage());
        }
        UserDto userDto = null;
        if(savedUser != null){
            userDto = new ModelMapper().map(savedUser, UserDto.class);
        }else{
            throw new RuntimeException("Failed to update user: Save operation returned null");
        }
        return userDto;
    }

    @Override
    public boolean changePassword(UUID userId, UpdateUserPasswordRequest updateUserPasswordRequest) {
        Optional<UserEntity> user = userRepository.findById(userId);
        UserEntity savedUser;
        if(user.isPresent()){
            if(bCryptPasswordEncoder.matches(updateUserPasswordRequest.getOldPassword(), user.get().getEncryptedPassword())){
                if(updateUserPasswordRequest.getNewPassword().equals(updateUserPasswordRequest.getNewPasswordConfirm())){
                    user.get().setEncryptedPassword(bCryptPasswordEncoder.encode(updateUserPasswordRequest.getNewPassword()));
                    try{
                        savedUser = userRepository.save(user.get());
                        return true;
                    }catch(DataAccessException ex){
                        throw new UserUpdateException(String.format("Failed to update user with id %s ", user.get().getId().toString()) + ". Additional message: " + ex.getMessage());
                    }
                }
            }else{
                throw new MismatchedPasswordException("Given passwords do not match");
            }
        }else{
            throw new UserNotFoundException(String.format("User with id %s not found", userId.toString()));
        }
        return false;
    }
}
