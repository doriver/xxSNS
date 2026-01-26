package com.example.sns.modules.post.application;

import com.example.sns.common.exception.ErrorCode;
import com.example.sns.common.exception.Expected5xxException;
import com.example.sns.modules.post.domain.entity.Comment;
import com.example.sns.modules.post.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentListByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId);
    }

    public Long addComment(Long userId, Long postId, String username, String content) {
        Comment comment = Comment.builder()
                .userId(userId).postId(postId)
                .username(username).content(content)
                .build();

        Comment savedComment = commentRepository.save(comment);
        if (savedComment == null) {
            log.error("[CommentBO addComment] save()실패");
            throw new Expected5xxException(ErrorCode.FAIL_CREATE_COMMENT);
        }
        return savedComment.getId();
    }
}
