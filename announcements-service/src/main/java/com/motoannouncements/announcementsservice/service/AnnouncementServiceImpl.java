package com.motoannouncements.announcementsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoannouncements.announcementsservice.dto.AnnouncementFilterRequest;
import com.motoannouncements.announcementsservice.dto.CarBikeRecognitionModelResponse;
import com.motoannouncements.announcementsservice.dto.UserResponseModel;
import com.motoannouncements.announcementsservice.exceptions.AnnouncementNotFoundException;
import com.motoannouncements.announcementsservice.repository.CommentRepository;
import com.motoannouncements.announcementsservice.specifications.AnnouncementSpecifications;
import com.motoannouncements.basedomains.dto.AnnouncementDto;
import com.motoannouncements.announcementsservice.dto.CreateAnnouncementRequest;
import com.motoannouncements.announcementsservice.entity.AnnouncementCommentEntity;
import com.motoannouncements.announcementsservice.entity.AnnouncementEntity;
import com.motoannouncements.announcementsservice.entity.S3BucketImageEntity;
import com.motoannouncements.announcementsservice.repository.AnnouncementRepository;

import com.motoannouncements.enums.AnnouncementCategoryType;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService{
    private final AnnouncementRepository announcementRepository;
    private final CommentRepository commentRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
    private final RestTemplate restTemplate;

    @Override
    public AnnouncementDto addAnnouncementDtoImagesFileKeys(AnnouncementDto announcementDto, List<String> uploadedFilesKeys) {
        for(int i = 0; i < uploadedFilesKeys.size(); i++){
            announcementDto.getImages().add(new AnnouncementDto.S3BucketImageDto(uploadedFilesKeys.get(i), announcementDto.getId()));
        }
        return announcementDto;
    }

    @Override
    public AnnouncementEntity saveAnnouncement(AnnouncementDto announcementDto) {
        AnnouncementEntity announcementEntity = new ModelMapper().map(announcementDto, AnnouncementEntity.class);
        AnnouncementEntity savedAnnouncement = null;
        try{
            savedAnnouncement = announcementRepository.save(announcementEntity);
        }catch (DataAccessException ex){
            //TODO: create CreateAnnoucementException to handle it globally and return ResponseEntity
            log.info("DataAccessException in createAnnoucemenet method: " + ex.getMessage());
        }
        return savedAnnouncement;
    }

    @Override
    public AnnouncementEntity saveAnnouncement(AnnouncementEntity announcementEntity) {
        AnnouncementEntity savedAnnouncement = null;
        try{
            savedAnnouncement = announcementRepository.save(announcementEntity);
        }catch (DataAccessException ex){
            //TODO: create CreateAnnoucementException to handle it globally and return ResponseEntity
            log.info("DataAccessException in createAnnoucemenet method: " + ex.getMessage());
        }
        return savedAnnouncement;
    }

    @Override
    public AnnouncementEntity createAnnouncement(AnnouncementDto announcementDto, CreateAnnouncementRequest createAnnouncementRequest) {
        AnnouncementEntity savedAnnouncement = new AnnouncementEntity();

        // should be changed to properly configured ModelMapper (but it's screaming errors)
        announcementDto.setDescription(createAnnouncementRequest.getDescription());
        announcementDto.setAuthorId(createAnnouncementRequest.getAuthorId());
        announcementDto.setYearOfProduction(createAnnouncementRequest.getYearOfProduction());
        announcementDto.setPrice(createAnnouncementRequest.getPrice());
        announcementDto.setCondition(createAnnouncementRequest.getCondition());
        announcementDto.setCategory(createAnnouncementRequest.getCategory());
        announcementDto.setBrand(createAnnouncementRequest.getBrand());
        announcementDto.setModel(createAnnouncementRequest.getModel());
        announcementDto.setImported(createAnnouncementRequest.isImported());
        announcementDto.setCubicCapacity(createAnnouncementRequest.getCubicCapacity());
        announcementDto.setFuelType(createAnnouncementRequest.getFuelType());
        announcementDto.setPower(createAnnouncementRequest.getPower());
        announcementDto.setGearboxType(createAnnouncementRequest.getGearboxType());
        announcementDto.setDriveType(createAnnouncementRequest.getDriveType());
        announcementDto.setNumberOfDoors(createAnnouncementRequest.getNumberOfDoors());
        announcementDto.setNumberOfSeats(createAnnouncementRequest.getNumberOfSeats());
        announcementDto.setColor(createAnnouncementRequest.getColor());
        announcementDto.setCountryOfOrigin(createAnnouncementRequest.getCountryOfOrigin());
        announcementDto.setAccidentFree(createAnnouncementRequest.isAccidentFree());

        AnnouncementEntity announcementEntity = new ModelMapper().map(announcementDto, AnnouncementEntity.class);

        try{
            savedAnnouncement = announcementRepository.save(announcementEntity);
        }catch(DataAccessException e){
            log.info(e.getMessage());
        }
        return savedAnnouncement;
    }

    @Override
    public List<InputStream> mapMultipartFilesToInputStream(List<MultipartFile> multipart) throws RuntimeException {
        List<InputStream> inputStreams = new ArrayList<InputStream>();
        try {
            // Convert MultipartFile objects to InputStreams
            inputStreams = multipart.stream()
                    .map(file -> {
                        try {
                            return file.getInputStream();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to convert file to input stream: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return inputStreams;
    }

    @Override
    public List<AnnouncementEntity> getAnnouncements(UUID userId) {
        List<AnnouncementEntity> announcementEntityList = announcementRepository.findByAuthorId(userId);
        return announcementEntityList;
    }

    @Override
    public AnnouncementEntity getAnnouncement(UUID announcementId) {
        Optional<AnnouncementEntity> annoucenement = announcementRepository.findById(announcementId);
        if(annoucenement.isPresent()){
            return annoucenement.get();
        }else{
            throw new AnnouncementNotFoundException(String.format("Announcement with id %s not found", announcementId.toString()));
        }
    }

    @Override
    public List<AnnouncementEntity> filterAnnouncements(AnnouncementFilterRequest filterRequest) {
        Specification<AnnouncementEntity> specification = AnnouncementSpecifications.filterByCriteria(filterRequest);
        return announcementRepository.findAll(specification);
    }

    private String toJson(Object object){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponseModel getUserData(UUID userId, String token){
        String url = String.format("http://users-ws/api/user/%s", userId);
        String json = toJson(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        UserResponseModel response = WebClient.builder()
                .filter(lbFunction)
                .build()
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(UserResponseModel.class)
                .block();

        return response;
    }

    @Override
    public Map<UUID, UserResponseModel> getUsersData(List<UUID> userIds, String token) {
        String url = String.format("http://users-ws/api/user/usersdata");
        String json = toJson(userIds);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        List<UserResponseModel> response = WebClient.builder()
                .filter(lbFunction)
//                .exchangeStrategies(strategies)
                .baseUrl(url)
                .defaultHeader("Authorization", token)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToFlux(UserResponseModel.class)
                .collectList()
                .block();

        // Convert the list into a HashMap with UUID as the key
        Map<UUID, UserResponseModel> responseMap = response.stream()
                .collect(Collectors.toMap(UserResponseModel::getId, Function.identity()));

        return responseMap;
    }

    @Override
    public void addImage(S3BucketImageEntity image) {
        // comments out for testing
//        images.add(image);
//        image.setAnnouncement(this);
    }

    @Override
    public void removeImage(UUID image_id) {
        // TODO: create removal functionality based on passed UUID to delete data from s3
    }

    @Override
    public void addComment(AnnouncementCommentEntity comment) {
//        comments.add(comment);
//        comment.setAnnouncement(this);
    }

    @Override
    public void deleteComment(UUID comment_id) {
        commentRepository.deleteById(comment_id);
    }

    @Override
    public boolean isCarBikeValidImages(List<MultipartFile> multipart, AnnouncementCategoryType announcementCategoryType) {
        String url = "http://127.0.0.1:9090/recognition/car-bike/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        for (MultipartFile file : multipart) {
            byte[] fileBytes = new byte[0];
            try {
                fileBytes = file.getBytes();
            } catch (IOException e) {
                // Handle the exception as needed
            }
            Resource fileResource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("images", fileResource);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<List<CarBikeRecognitionModelResponse>> response = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<CarBikeRecognitionModelResponse>>() {
        });

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Boolean> validations = new ArrayList<>();
            List<CarBikeRecognitionModelResponse> responseBody = response.getBody();
            for(CarBikeRecognitionModelResponse reco : responseBody){
                if (reco.getRecognition_class().equals(announcementCategoryType.toString().toLowerCase()) && Float.parseFloat(reco.getProbability()) > 95.00){
                    validations.add(true);
                }else{
                    validations.add(false);
                }
            }

            boolean allTrue = areAllTrue(validations);
            return allTrue;

        } else {
            // Handle errors or non-OK responses
        }
        return false;
    }
    public boolean areAllTrue(List<Boolean> booleanList) {
        for (boolean value : booleanList) {
            if (!value) {
                return false; // Found a false element, not all are true
            }
        }
        return true; // All elements are true
    }


}
