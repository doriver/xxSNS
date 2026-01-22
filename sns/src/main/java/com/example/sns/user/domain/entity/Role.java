package com.example.sns.user.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
    DB에는 ADMIN 하나만 저장되어 있더라도
    Spring Security 메모리 상에는 ROLE_ADMIN, ROLE_TEACHER, ROLE_MENTOR, ROLE_USER를 모두 가진 것으로 간주하게 만듭니다.
 */
public enum Role {
    USER("ROLE_USER", Collections.emptyList()),
    MENTOR("ROLE_MENTOR", List.of(USER)),
    ADMIN("ROLE_ADMIN", List.of(MENTOR, USER));

    private final String key;
    private final List<Role> children;

    Role(String key, List<Role> children) {
        this.key = key;
        this.children = children;
    }

    // 자기 자신과 하위 모든 권한을 String 리스트로 반환
    public List<String> getHierarchyAuthorities() {
        List<String> authorities = new ArrayList<>();
        authorities.add(this.key);
        for (Role child : children) {
            authorities.add(child.key);
        }
        return authorities;
    }

    public String getKey() {
        return this.key;
    }
}
