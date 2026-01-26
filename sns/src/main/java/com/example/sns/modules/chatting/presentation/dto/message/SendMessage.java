package com.example.sns.modules.chatting.presentation.dto.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendMessage {
    private String sender;
    private long senderId;
    private String message;
}
