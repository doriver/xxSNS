package com.example.sns.post.presentation;

import com.example.sns.common.ApiResponse;
import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.post.application.LikeService;
import com.example.sns.post.application.PostService;
import com.example.sns.post.presentation.dto.PostSaveDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

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

    // 게시글 좋아요 + 좋아요 취소
    @GetMapping("/likes/{postId}")
    public ApiResponse<Map<String, Object>> likePost(
            @PathVariable("postId") Long postId
            , UserInfo userInfo) {

        Long userId = userInfo.getUserId();

        boolean isLike = likeService.like(postId, userId);
        int likeCount = likeService.countLike(postId);

        Map<String, Object> result = new HashMap<>();

        result.put("like", isLike);
        result.put("likeCount", likeCount);

        return ApiResponse.success(result);
    }
}
