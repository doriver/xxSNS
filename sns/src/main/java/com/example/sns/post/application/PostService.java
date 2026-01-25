package com.example.sns.post.application;

import com.example.sns.post.domain.PostWithOthers;
import com.example.sns.post.domain.entity.Comment;
import com.example.sns.post.domain.entity.Post;
import com.example.sns.post.domain.repository.PostRespository;
import com.example.sns.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRespository postRespository;
    private final CommentService commentService;
    private final LikeService likeService;
    private final UserService userService;

    public List<PostWithOthers> getTimeLine(Long myUserId) {
        List<Post> postList = postRespository.findAllByOrderByIdDesc();

        List<PostWithOthers> postWithOthersList = new LinkedList<>();

        for(Post post:postList) {
            List<Comment> commentList = commentService.getCommentListByPostIdType(post.getId());

            boolean isLike = likeService.existLike(post.getId(), myUserId);
            int likeCount = likeService.countLike(post.getId());

            String writerProfileImage = userService.getProfileImage(post.getUserId());

            PostWithOthers postWithOthers
                    = PostWithOthers.builder()
                    .post(post).writerProfileImage(writerProfileImage)
                    .commentList(commentList).isLike(isLike).likeCount(likeCount)
                    .build();

            postWithOthersList.add(postWithOthers);
        }

        return postWithOthersList;
    }
}
