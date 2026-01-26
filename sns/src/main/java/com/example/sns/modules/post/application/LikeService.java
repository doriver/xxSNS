package com.example.sns.modules.post.application;

import com.example.sns.modules.post.domain.repository.LikeRedisDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRedisDAO redisDAO;

    //사용자기준 좋아요 여부, 좋아요 되있으면 true, 안되있으면 false
    public boolean existLike(Long postId, Long userId) {
        String key = "" + postId;
        return redisDAO.sismember(key, Long.toString(userId)); // 해당글 좋아요에 사용자id 있는지 여부
    }

    // 좋아요 되고 true, 좋아요 취소되고 false
    @Transactional // 이 메소드가 트랜잭션 내에서 실행되어야 함을 나타냄
    public boolean like(Long postId, Long userId) {
        String key = "" + postId;

        // 만약 해당 포스트에 좋아요가 되어 있다면 좋아요 취소하고 , false
        if(this.existLike(postId, userId)) {
            redisDAO.srem(key, Long.toString(userId)); // 해당글 좋아요에서 사용자id 삭제
            return false;
        } else  {  // 만약 해당 포스트에 좋아요가 안되어 있다면 좋아요되고 , true
            redisDAO.sadd(key, Long.toString(userId)); // 해당글 좋아요에 사용자id 추가
            return true;
        }
    }

    // 좋아요 갯수
    public int countLike(Long postId) {
        String key = "" + postId;
        return (redisDAO.scard(key)).intValue(); // 해당글 좋아요 원소개수 얻음
    }

    public void deleteLikeInPost(Long postId) {
        String key = "" + postId;
        redisDAO.del(key); // 해당글에 대응하는 key 삭제
    }
}
