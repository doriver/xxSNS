package com.example.sns.modules.chatting.presentation;


import com.example.sns.common.utils.JsonHandling;
import com.example.sns.common.utils.TimeFormat;
import com.example.sns.modules.chatting.application.ChatMessageService;
import com.example.sns.modules.chatting.domain.dto.ChatMessageRedisDTO;
import com.example.sns.modules.chatting.presentation.dto.message.RecieveMessage;
import com.example.sns.modules.chatting.presentation.dto.message.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompChattingController {

    private final ChatMessageService chatMessageService;
    private final RedisTemplate<String, String> stompPubSubRedisTemplate;

    @MessageMapping("/{roomId}")
    public void messageHandler(SendMessage sendMessage
                                , @DestinationVariable("roomId") long roomId) {

        String sender = sendMessage.getSender();
        long senderId = sendMessage.getSenderId();
        String message = sendMessage.getMessage();
        LocalDateTime sendedAt = LocalDateTime.now();

        try {
            chatMessageService.saveMessageRedis(roomId
                    , new ChatMessageRedisDTO(senderId, message, sendedAt));
        } catch(Exception e) {
            log.error("roomId={} 에서 채팅메시지 저장 살패 senderId={}", roomId, senderId);
            log.error("",e);
        }

        TimeFormat timeFormat = new TimeFormat();
        String formattedSendedAt = timeFormat.hourMinute(sendedAt);

        RecieveMessage msg = RecieveMessage.builder()
                .content(message).sender(sender).sendedAt(formattedSendedAt)
                .build();

        // publish to redis
        String jsonString = JsonHandling.objToJsonString(msg);
        stompPubSubRedisTemplate.convertAndSend("MchatRoom:" + roomId, jsonString);
    }
}
