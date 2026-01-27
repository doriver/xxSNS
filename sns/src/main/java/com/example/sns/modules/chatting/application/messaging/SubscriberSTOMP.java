package com.example.sns.modules.chatting.application.messaging;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriberSTOMP implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte @Nullable [] pattern) {
        // 실제 메시지가 온 채널명 추출 (예: "chatRoom:123")
        String fullChannel = new String(message.getChannel());

        if (fullChannel.startsWith("chatRoom:")) {
            // "chatRoom:123"에서 "123"만 추출하기
            String roomId = fullChannel.replace("chatRoom:", "");
            // Redis에서 받은 byte[]를 JSON 문자열로 변환
            String publishMessage = new String(message.getBody());
            // STOMP로 클라이언트들에게 broadcasting
            messagingTemplate.convertAndSend("/chatRoom/" + roomId + "/message", publishMessage);
        }
    }
}
