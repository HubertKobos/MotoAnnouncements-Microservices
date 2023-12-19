package com.motoannouncements.chatservice.redis;

import com.motoannouncements.chatservice.chat.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, MessageDto> redisTemplate;

    @Autowired
    public RedisMessageSubscriber(SimpMessagingTemplate messagingTemplate, RedisTemplate<String, MessageDto> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // Handle the received message from Redis.
        // In this example, we assume that the message is of type MessageDto.
        MessageDto dto = (MessageDto) redisTemplate.getValueSerializer().deserialize(message.getBody());

        // Send the received message to the appropriate WebSocket channel.
        String topic = Arrays.toString(message.getChannel()); // Get the channel name from the Redis message.
        messagingTemplate.convertAndSend("/topic/" + "asd", dto);
    }
}