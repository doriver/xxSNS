package com.example.sns.modules.post.domain.repository;

import com.example.sns.modules.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRespository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc();
    List<Post> findAllByUserIdOrderByIdDesc(Long userId);
}
