package com.example.sns.views;

import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.modules.chatting.application.ChatRoomService;
import com.example.sns.modules.chatting.domain.repository.ChatParticipantRepository;
import com.example.sns.modules.chatting.domain.repository.ChatRoomRepository;
import com.example.sns.modules.chatting.presentation.dto.response.ChatRoomInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chatting-view")
@RequiredArgsConstructor
public class ChattingViewController {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;


    /*
        단톡방 목록 화면
        접근범위(권한) : all
     */
    @GetMapping("/list")
    public String chatListView(Model model, UserInfo userInfo) {
        List<ChatRoomInfoResponse> chatRoomList = chatRoomService.getChatRoomListView();
        model.addAttribute("chatRoomList", chatRoomList);
        Long userId = userInfo.getUserId();
        if (userId != null) {
            model.addAttribute("userId", userId);
        }
        return "chatting/chatList";
    }

    /*
        단통방 화면
        접근범위(권한) : 로그인
        @PostMapping("/participant/{rid}") enterRoom() 에서만 접근가능 하도록 설계됨
     */
    @GetMapping("/rooms/{roomId}")
    public String chatView(@PathVariable("roomId") long roomId, UserInfo userInfo, Model model) {
        chatRoomService.roomViewSetting(roomId, model);

        model.addAttribute("userId", userInfo.getUserId());
        model.addAttribute("nickname", userInfo.getUsername());

        return "chatting/chatRoom";
    }

}
