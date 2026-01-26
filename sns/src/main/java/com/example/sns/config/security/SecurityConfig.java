package com.example.sns.config.security;

import com.example.sns.common.AppConstants;
import com.example.sns.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .formLogin( form -> form.disable())

                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // HTTP요청에 대한 권한 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(AppConstants.WHITELIST_URLS).permitAll()
                        .requestMatchers(AppConstants.MENTOR_URLS).hasRole("MENTOR")
                        .requestMatchers(AppConstants.ADMIN_URLS).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class)

                // 로그아웃 기능
                .logout(logout -> logout
                        .logoutUrl("/sign-out")
                        .deleteCookies("Authorization")
                        .logoutSuccessUrl("/sign-view")
                )
        ;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
