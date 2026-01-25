package com.example.sns.post.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRedisDAO {
    private final RedisTemplate<String, Object> redisTemplate05;

    // sadd : 집합에 value추가
    public Long sadd(String key, String value) {
        return redisTemplate05.opsForSet().add(key, value);
    }
    // scard : 집합 원소개수 얻믕
    public Long scard(String key) {
        return redisTemplate05.opsForSet().size(key);
    }
    // sismember : 집합에 value값 존재하는지 확인
    public Boolean sismember(String key, String value) {
        return redisTemplate05.opsForSet().isMember(key, value);
    }
    // srem : 집합에서 해당원소 제거
    public Long srem(String key, String value) {
        return redisTemplate05.opsForSet().remove(key, value);
    }
    // del : 해당key 데이터 삭제
    public void del(String key) {
        redisTemplate05.delete(key);
    }
}
