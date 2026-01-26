package com.example.sns.modules.user.domain.repository;


import com.example.sns.modules.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    long countByUsername(String username);

    @Query("SELECT profileImage FROM User WHERE id = :id") // From뒤에 테이블 이름이 아니라 entity이름 와야함
    String findProfileImageById(@Param("id") Long id);
}
