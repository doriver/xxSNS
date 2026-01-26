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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostViewController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/post-view")
    public String postView(UserInfo userInfo, Model model) {

        Long myUserId = userInfo.getUserId();

        List<PostWithOthers> postList = postService.getTimeLine(myUserId);

        User myInfo = userService.userInformation(myUserId);
        model.addAttribute("postList", postList);
        model.addAttribute("myInfo", myInfo);
        return "postTimeline";
    }
}
