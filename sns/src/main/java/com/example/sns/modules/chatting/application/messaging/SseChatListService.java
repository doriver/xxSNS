package com.example.sns.modules.chatting.application.messaging;

import com.example.sns.common.exception.ErrorCode;
import com.example.sns.common.exception.Expected5xxException;
import com.example.sns.modules.chatting.presentation.dto.response.ChatRoomInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseChatListService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    // roomId별로 SseEmitter들 따로 저장해야 할수도

    public void addEmitter(SseEmitter emitter) {
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            log.info("SseEmitter.onCompletion : {}", emitter);
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            log.info("SseEmitter.onTimeout : {}", emitter);
        });
        emitter.onError((e) -> {
            emitter.complete();
            log.error("SseEmitter.onError: {} -- {}", emitter, e.getMessage());
//            log.error("emitter.onError", e);
        });
        emitters.add(emitter);
        log.info("add SseEmitter: {}", emitter);
    }

    public void sendEventCountUp(String roomId) {
        for (SseEmitter emitter : emitters) {
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("countUp").data(roomId));
                } catch (IOException ignored) { }
            }
        }
    }

    public void sendEventCountDown(String roomId) {
        for (SseEmitter emitter : emitters) {
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("countDown").data(roomId));
                } catch (IOException ignored) { }
            }
        }
    }

    public void sendEventEndRoom(String roomId) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("roomEnd").data(roomId));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }

    public void sendEventCreationRoom(String chatRoomJson) {

        ChatRoomInfoResponse chatRoomInfoResponse = convertFromJson(chatRoomJson);
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("roomCreation").data(chatRoomInfoResponse, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                emitter.complete();
            }
        }
    }

    private ChatRoomInfoResponse convertFromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, ChatRoomInfoResponse.class);
        } catch (Exception e) {
            throw new Expected5xxException(ErrorCode.FAIL_JSON_CONVERT);
        }
    }
}
