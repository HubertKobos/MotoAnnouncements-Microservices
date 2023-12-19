package com.motoannouncements.chatservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoannouncements.chatservice.dto.UserResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {

    private final RedisTemplate<String, String> redisTemplate;
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;


    @Autowired
    public ChatService(RedisTemplate<String, String> redisTemplate, WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction){
        this.redisTemplate = redisTemplate;
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }

    public List<String> findChatsByUserId(String userId){
        List<String> chatListKeys = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("chat:" + userId + ":*");
        for (String key : keys) {
            if (redisTemplate.type(key) == DataType.LIST) {
                chatListKeys.add(key);
            }
        }
        return chatListKeys;
    }
    private String toJson(Object object){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getLastMessages(String key, int count){
        return redisTemplate.opsForList().range(key, 0, count+1);
    }
    public List<String> getMessageReceiversIds(List<String> messagesKeys){
        /*
        Returns isolated ids of message receivers from full chat names (keys) from redis db (from format (chat:fromid:toid)
        */
        List<String> ids = messagesKeys.stream().map(str -> {
            int temp = 0;
            int index = 0;
            for(int i=0; i<str.length(); i++){
                if(temp == 2){
                    index = i;
                    break;
                }
                else if(str.charAt(i) == ':'){
                    temp += 1;
                }

            }
            return str.substring(index);
        }).collect(Collectors.toList());
        return ids;
    }

    public List<UserResponseModel> getUsersData(List<String> userIds){
        String url = "http://users-ws/api/user/usersdata";
        String json = toJson(userIds);
        List<UserResponseModel> response = WebClient.builder()
                .filter(lbFunction)
                .build().post().uri(url).body(BodyInserters.fromValue(userIds))
                .retrieve()
                .bodyToFlux(UserResponseModel.class)
                .collectList()
                        .block();

        return response;
    }
}
