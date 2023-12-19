package com.motoannouncements.chatservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoannouncements.chatservice.chat.MessageDto;
import com.motoannouncements.chatservice.dto.UserResponseModel;
import com.motoannouncements.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatControllerHTTP {


    private final ChatService chatService;

    @GetMapping("/{userId1}/{userId2}/{count}")
    public ResponseEntity<List<MessageDto>> getMessagesForChat(@PathVariable UUID userId1, @PathVariable UUID userId2, @PathVariable int count){

        UUID user1, user2;
        if (userId1.compareTo(userId2) < 0) {
            user1 = userId1;
            user2 = userId2;
        } else {
            user1 = userId2;
            user2 = userId1;
        }
        String chatKey = String.format("chat:%s:%s", user1, user2);
        List<String> listMessages = chatService.getLastMessages(chatKey, count);
        Collections.reverse(listMessages);
        ObjectMapper objectMapper = new ObjectMapper();
        List<MessageDto> messageDtoList = new ArrayList<>();
        // map from list of string to json objects
        for(String messageString: listMessages){
            try{
                MessageDto mess = objectMapper.readValue(messageString, MessageDto.class);
                messageDtoList.add(mess);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        log.info("LIST MESSAGES -->" + listMessages.toString());
        return ResponseEntity.ok().body(messageDtoList);
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity getConversations(@PathVariable String userId){
        List<String> chats = chatService.findChatsByUserId(String.valueOf(userId));
        List<String> receiversIds = chatService.getMessageReceiversIds(chats);
        // call with http request from service to user service to get user informations
        List<UserResponseModel> usersData = chatService.getUsersData(receiversIds);
        return ResponseEntity.ok().body(usersData);
    }

}
