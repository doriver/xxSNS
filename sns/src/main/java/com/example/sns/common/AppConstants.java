package com.example.sns.common;

public class AppConstants {

    // 인증/인가(Auth)
    public static final String[] WHITELIST_URLS = {


            "/", "/js/**", "/img/**", "/css/**", "/favicon.ico", "/api/all",
            "/sign-view", // 회원가입, 로그인 페이지
            "/users/sign/*", // 회원가입, 로그인 api

            "/sse/trigger", // 테스트용
            "/view/chat/*", // 채팅목록, 채팅방 페이지
//            "/api/users/join/*",
//            "/api/posts/*/details",


    };
    public static final String[] MENTOR_URLS = {
            "/api/role/mentor"
    };

    public static final String[] ADMIN_URLS = {
            "/api/role/admin"
    };
}
