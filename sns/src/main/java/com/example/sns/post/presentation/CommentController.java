package com.example.sns.post.presentation;

import com.example.sns.common.ApiResponse;
import com.example.sns.common.argumentResolver.UserInfo;
import com.example.sns.post.application.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 게시글 댓글 작성
    @PostMapping("/comments/{postId}")
    @ResponseBody
    public ApiResponse<?> investComment(@PathVariable("postId") Long postId
            , @RequestParam("content") String content
            , UserInfo userInfo) {

        Long userId = userInfo.getUserId();
        String username = userInfo.getUsername();

        Long savedCommentId = commentService.addComment(userId, postId, username, content);

        return ApiResponse.success();
    }
}
