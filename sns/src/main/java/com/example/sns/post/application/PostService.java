package com.example.sns.post.application;

import com.example.sns.common.FileManagerService;
import com.example.sns.common.exception.ErrorCode;
import com.example.sns.common.exception.Expected5xxException;
import com.example.sns.post.domain.PostWithOthers;
import com.example.sns.post.domain.entity.Comment;
import com.example.sns.post.domain.entity.Post;
import com.example.sns.post.domain.repository.PostRespository;
import com.example.sns.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRespository postRespository;
    private final CommentService commentService;
    private final LikeService likeService;
    private final UserService userService;

    @Transactional
    public Long addPost(Long userId, String username, String content, MultipartFile file) {

        String filePath = null;

        if(file != null) {
            FileManagerService fileManager = new FileManagerService();
            filePath = fileManager.saveFile(userId, file);

            if(filePath == null) {
                log.error("saveFile() 실패");
                throw new Expected5xxException(ErrorCode.FAIL_SAVE_FILE);
            }
        }

        Post post = Post.builder()
                .userId(userId).username(username).content(content).imagePath(filePath)
                .build();

        Post savedPost = postRespository.save(post);
        if (savedPost == null) {
            log.error("[PostBO addPost] save()실패");
            throw new Expected5xxException(ErrorCode.FAIL_CREATE_POST);
        }

        return savedPost.getId();
    }

    public List<PostWithOthers> getTimeLine(Long myUserId) {
        List<Post> postList = postRespository.findAllByOrderByIdDesc();

        List<PostWithOthers> postWithOthersList = new LinkedList<>();

        for(Post post:postList) {
            List<Comment> commentList = commentService.getCommentListByPostId(post.getId());

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
