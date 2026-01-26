package com.example.sns.post.presentation;

import com.example.sns.common.ApiResponse;
import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.post.application.PostService;
import com.example.sns.post.presentation.dto.PostSaveDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/posts")
    @ResponseBody
    public ApiResponse<?> postCreate(@Valid @ModelAttribute PostSaveDTO form
            , UserInfo userInfo) {

        Long userId = userInfo.getUserId();
        String username = userInfo.getUsername();

        Long savedPostId = postService.addPost(userId, username, form.getContent(), form.getFile());

        return ApiResponse.success();
    }
}
