package com.example.sns.view;

import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.post.application.PostService;
import com.example.sns.post.domain.PostWithOthers;
import com.example.sns.user.application.UserService;
import com.example.sns.user.domain.entity.User;
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
