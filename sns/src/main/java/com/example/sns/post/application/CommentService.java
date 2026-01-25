package com.example.sns.post.application;

import com.example.sns.post.domain.entity.Comment;
import com.example.sns.post.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentListByPostIdType(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId);
    }
}
