package com.example.sns.common.utils;

import com.example.sns.modules.chatting.domain.entity.ChatParticipant;
import com.example.sns.modules.chatting.domain.entity.ChatRoom;
import com.example.sns.modules.chatting.domain.repository.ChatParticipantRepository;
import com.example.sns.modules.chatting.presentation.dto.response.ChatRoomInfoResponse;
import com.example.sns.modules.chatting.presentation.dto.response.ParticipantDTO;
import com.example.sns.modules.chatting.presentation.dto.response.RoomViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ToDto {

    private final ChatParticipantRepository chatParticipantRepository;

    /*
        채팅방 목록 화면에서, 채팅방에 필요한 데이터들로 얻기
     */
    public ChatRoomInfoResponse chatRoomEntityToDto(ChatRoom entity) {

        Long currentParticipants = chatParticipantRepository.countByRoomAndExitAt(entity, null);

        TimeFormat timeFormat = new TimeFormat();
        LocalDateTime createdAt = (entity.getCreatedAt() != null) // Entity만들때 java쪽에선 null로 들어가고, db쪽에선 CURRENT_TIMESTAMP로 되서
                ? entity.getCreatedAt() : LocalDateTime.now();
        String formattedCreatedAt = timeFormat.hourMinute(createdAt);

        ChatRoomInfoResponse chatRoomDTO = ChatRoomInfoResponse.builder()
                .id(entity.getId())
                .mentor(entity.getMentor().getUsername())
                .roomName(entity.getRoomName()).createdAt(formattedCreatedAt)
                .userLimit(entity.getUserLimit()).userCount(currentParticipants.intValue())
                .build();

        return chatRoomDTO;
    }

    public ParticipantDTO chatParticipantEntityToDto(ChatParticipant participant) {
         return ParticipantDTO.builder()
                .participantId(participant.getId()).nickname(participant.getChatter().getUsername())
                .build();
    }

    public RoomViewResponse chatRoomAndParticipantsToDto(ChatRoom room, List<ParticipantDTO> participantList) {
      return RoomViewResponse.builder()
              .roomId(room.getId()).mentorId(room.getMentor().getId()).mentor(room.getMentor().getUsername())
              .roomName(room.getRoomName()).userLimit(room.getUserLimit())
              .chatterList(participantList)
              .build();
    }


}
