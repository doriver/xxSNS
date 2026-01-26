package com.example.sns.views;

import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.modules.post.application.PostService;
import com.example.sns.modules.post.domain.PostWithOthers;
import com.example.sns.modules.user.application.UserService;
import com.example.sns.modules.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;
    private final PostService postService;

    // 개인 홈 화면
    @GetMapping("/individual-view")
    public String individualHome(@RequestParam("userId") Long userId
            , UserInfo userInfo, Model model) {

        Long myUserId = userInfo.getUserId();

        User user = userService.userInformation(userId);
        List<PostWithOthers> postList = postService.getTimeLineByUserId(userId);
        User myInfo = userService.userInformation(myUserId);

        model.addAttribute("postList", postList);
        model.addAttribute("userInfo", user);
        model.addAttribute("myInfo", myInfo);

        return "individualHome";
    }
}
