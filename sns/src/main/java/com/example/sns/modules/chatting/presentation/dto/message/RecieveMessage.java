package com.example.sns.modules.chatting.presentation.dto.message;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecieveMessage {
    private String content;
    private String sender;
    private String sendedAt;

}
