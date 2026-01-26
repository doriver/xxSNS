package com.example.sns.modules.chatting.presentation.api;

import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.modules.chatting.application.ChatParticipanceService;
import com.example.sns.modules.chatting.application.MentorService;
import com.example.sns.modules.chatting.presentation.dto.request.RoomCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingApiController {

    private final MentorService mentorService;
    private final ChatParticipanceService chatParticipanceService;

    /*
                단톡방 입장하기
        접근범위(권한) : 로그인
        단톡방 목록에서 단톡방별로, 버튼(참여)을 눌러야만 동작하게 설계되있음
     */
    @PostMapping("/participants/rooms/{roomId}")
    public String enterRoom(@PathVariable("roomId") long roomId
                            , UserInfo userInfo) {
        chatParticipanceService.userEnterRoom(roomId, userInfo.getUserId());
        return "redirect:/view/chatting/rooms/" + roomId;
    }

    /*
                채팅방 나가기
        접근범위(권한) : 로그인
        단톡방에서 버튼(나가기)을 눌러야만 동작하게 설계되있음
     */
    @PatchMapping("/participants/rooms/{roomId}")
    @ResponseBody
    public void exitRoom(@PathVariable("roomId") long roomId, UserInfo userInfo) {
        chatParticipanceService.chatterExitRoom(roomId, userInfo.getUserId());
    }

    /*
                채팅방 종료
        접근범위(권한) : mentor
        단톡방에서 해당방을 개설한 멘토만 동작 가능
     */
    @PatchMapping("/rooms/{roomId}")
    @ResponseBody
    public void endRoom(@PathVariable("roomId") long roomId, UserInfo userInfo) {
        mentorService.mentorEndRoom(userInfo.getUserId(), roomId);
    }

    /*
                단체 채팅방 생성
        접근범위(권한) : mentor
     */
    @PostMapping("/rooms")
    @ResponseBody
    public void createRoom(@Valid @RequestBody RoomCreateRequest roomCreateRequest
                            , UserInfo userInfo) {
        mentorService.mentorCreateRoom(userInfo.getUserId()
                , roomCreateRequest.getRoomName(), roomCreateRequest.getUserLimit());
    }

}
