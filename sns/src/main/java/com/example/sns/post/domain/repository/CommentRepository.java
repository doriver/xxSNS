package com.example.sns.post.domain.repository;

import com.example.sns.post.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByIdDesc(Long postId); // 해닥 게시글에서 댓글 가져오기

}
