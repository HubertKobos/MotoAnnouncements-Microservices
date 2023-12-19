package com.motoannouncements.chatservice.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisTemplate<String, MessageDto> redisTemplate;
    private final BoundListOperations<String, MessageDto> messageDtoBoundListOperations;
    public MessageController(SimpMessagingTemplate simpMessagingTemplate, RedisTemplate<String, MessageDto> redisTemplate, BoundListOperations<String, MessageDto> messageDtoBoundListOperations) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.redisTemplate = redisTemplate;
        this.messageDtoBoundListOperations = messageDtoBoundListOperations;
    }

    // Handles messages from /app/chat. (Note the Spring adds the /app prefix for us).
    @MessageMapping("/chat")
    public MessageDto sendMessages(@Payload MessageDto dto, @Header("Message-From-userId") UUID fromUserId, @Header("Message-To-userId") UUID toUserId){
        log.info("topic --> " + fromUserId + " " + toUserId);
        log.info("received message --> " + dto);
        UUID user1, user2;
        if (fromUserId.compareTo(toUserId) < 0) {
            user1 = fromUserId;
            user2 = toUserId;
        } else {
            user1 = toUserId;
            user2 = fromUserId;
        }
        String chatKey = String.format("chat:%s:%s", user1, user2);
        redisTemplate.opsForList().leftPush(chatKey, dto);

        simpMessagingTemplate.convertAndSend("/topic/" + "asd", dto);



        List<MessageDto> messages = messageDtoBoundListOperations.range(0, -1);
        return dto;
    }


}
