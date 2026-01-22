package com.example.sns.config.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
    Jwt를 복호화해서, SecurityContextHolder에 넣을 Authentication를 만드는데 사용됨
    (내부에서 요청 처리할때, SecurityContextHolder에서 Authentication꺼낼때 실제 모습)
 */
public class MiniUserDetails implements UserDetails {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Long userId;
    private final String userNickname;

    public MiniUserDetails(Long userId, String userNickname, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.authorities = authorities;
    }

    public Long getId() {
        return userId;
    }

    public String getNickname() {
        return userNickname;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
