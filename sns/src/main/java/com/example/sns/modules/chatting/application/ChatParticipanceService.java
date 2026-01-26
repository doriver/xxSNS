package com.example.sns.modules.chatting.application;

import com.example.sns.common.exception.ErrorCode;
import com.example.sns.common.exception.Expected4xxException;
import com.example.sns.common.exception.Expected5xxException;
import com.example.sns.modules.chatting.application.messaging.ParticipantChangeEvent;
import com.example.sns.modules.chatting.domain.entity.ChatParticipant;
import com.example.sns.modules.chatting.domain.entity.ChatRoom;
import com.example.sns.modules.chatting.domain.repository.ChatParticipantRepository;
import com.example.sns.modules.chatting.domain.repository.ChatRoomRepository;
import com.example.sns.modules.user.domain.entity.User;
import com.example.sns.modules.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatParticipanceService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    private final ParticipantChangeEvent participantChangeEvent;


    /*
        채팅방 참석자가, 해당방 나가는 기능
    */
    public void chatterExitRoom(long roomId, long chatterId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new Expected4xxException(ErrorCode.ROOM_NOT_FOUND));
        User chatter = userRepository.findById(chatterId).orElse(null);

        ChatParticipant chatParticipant = chatParticipantRepository.findByChatterAndRoomAndExitAt(chatter, chatRoom, null).orElse(null);
        if (chatParticipant != null) {
            chatParticipant.stampExitTime(LocalDateTime.now());
            try {
                chatParticipantRepository.save(chatParticipant);
            } catch (Exception e) {
                throw new Expected5xxException(ErrorCode.FAIL_EXIT_ROOM);
            }
            try {
                participantChangeEvent.inAndOut(0, roomId
                        , chatParticipant.getId(), chatParticipant.getChatter().getUsername());
            } catch (Exception ignored) {}
        } // chatParticipant == null 인경우도 throw ex할필요없음, 프론트에서 location.href="/view/chatting/list"; 처리
    }

    /*
        사용자가 단톡방 입장하기
     */
    public void userEnterRoom(long rid, long uid) {
        ChatRoom chatRoom = chatRoomRepository.findById(rid)
                .orElseThrow(() -> new Expected4xxException(ErrorCode.ROOM_NOT_FOUND));

        // 입장 제한 로직
        Long currentParticipants = chatParticipantRepository.countByRoomAndExitAt(chatRoom, null);
        if (currentParticipants >= chatRoom.getUserLimit()) {
            throw new Expected4xxException(ErrorCode.ROOM_USER_LIMIT);
        }

        User participant = userRepository.findById(uid).orElse(null);
        ChatParticipant chatAttendance = chatParticipantRepository.findByChatterAndRoomAndExitAt(participant, chatRoom, null).orElse(null);
        // 이미 참석해 있는경우는 변화x, 새롭게 참석하는 경우만 입장 로직 실행
        if (chatAttendance == null) {
            ChatParticipant chatter = ChatParticipant.builder()
                    .room(chatRoom).chatter(participant)
                    .build();

            ChatParticipant savedChatter = null;
            try {
                savedChatter = chatParticipantRepository.save(chatter);
            } catch (Exception e) {
                throw new Expected5xxException(ErrorCode.FAIL_ENTER_ROOM);
            }
            try {
                participantChangeEvent.inAndOut(1, rid
                        , savedChatter.getId(), savedChatter.getChatter().getUsername());
            } catch (Exception ignored) {}
        }
    }
}
