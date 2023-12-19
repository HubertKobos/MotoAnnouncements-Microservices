package com.motoannouncements.chatservice.sender;

import com.motoannouncements.chatservice.chat.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisSender {
    @Autowired
    private RedisTemplate<String, MessageDto> redisTemplate;
    @Autowired
    private ChannelTopic topic;
    public void sendDataToRedisQueue(MessageDto input){
        redisTemplate.convertAndSend(topic.getTopic(), input.toString());
        log.info("Data - " + input + " sent through Redis Topic - " + topic.getTopic());
    }
}
