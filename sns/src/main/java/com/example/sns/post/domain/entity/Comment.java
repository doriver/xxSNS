package com.example.sns.post.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_user", columnList = "userId")
        , @Index(name = "idx_post", columnList = "postId")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    @NotBlank
    private String username;

    @NotBlank
    private String content;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long postId, Long userId, String username, String content) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.content = content;
    }
}
