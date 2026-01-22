package com.example.sns.config.security;


import com.example.sns.common.AppConstants;
import com.example.sns.config.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        /*
            요청Header에서 Access JWT추출
            (Cookie로 돼 있음)
         */
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("Authorization")) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }

        /*
            AccessToken이 있는경우 -> 유효한지 판단
            AccessToken이 없는경우 -> 아무런 조치도 안하고 뒤로 넘김
         */
        if (accessToken != null) {
            boolean accessValid = jwtTokenProvider.validateToken(accessToken);
            /*
                AccessToken이 유효할때
                토큰의 인증정보(Authentication)를 SecurityContext에 저장해, Security필터가 인증권한 판단함
             */
            if (accessValid) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }


    /*
        SecurityConfig의 permitAll()에 해당하는 요청에 대해서 해당 필터 실행을 하지 않도록 하기 위함입니다.
        return값이 true면 해당 요청에대해 필터 실행x , 기본값은 false
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return PatternMatchUtils.simpleMatch(AppConstants.WHITELIST_URLS, request.getRequestURI());
    }
}
