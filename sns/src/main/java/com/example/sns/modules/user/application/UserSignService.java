package com.example.sns.modules.user.application;


import com.example.sns.config.security.jwt.JwtToken;
import com.example.sns.config.security.jwt.JwtTokenProvider;
import com.example.sns.modules.user.domain.entity.Role;
import com.example.sns.modules.user.domain.entity.User;
import com.example.sns.modules.user.domain.repository.UserRepository;
import com.example.sns.modules.user.presentation.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /*
            회원가입
        성공시, 저장된 userId값
        실패시, 실패메시지
    */
    public Map<String,Object> registerUser(UserSignUpDTO userSignUpDTO) {
        Map<String,Object> result = new HashMap<>();
        Optional<User> optionalUser = userRepository.findByUsername(userSignUpDTO.getUsername());

        if (optionalUser.isPresent()) {
            result.put("failMessage", "이미 존재하는 닉네임 입니다");
            return result;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptPassword = passwordEncoder.encode(userSignUpDTO.getPassword());

        if(encryptPassword.isEmpty()) {
            result.put("failMessage", "비밀번호 암호화 실패");
            return result;
        }
        User user = User.builder()
                .username(userSignUpDTO.getUsername()).password(encryptPassword)
                .role(Role.USER).email(userSignUpDTO.getEmail())
                .build();

        User savedUser = userRepository.save(user);
        result.put("successValue", savedUser.getId());
        return result;
    }

    // 로그인
    public JwtToken authenticateUser(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // username, password에 대한 검증
        // 메서드authenticate가 실행될 때 CustomUserDetailsService에서 만든 메서드loadUserByUsername가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject()
                                                            .authenticate(authenticationToken);
                // UsernamePasswordAuthenticationToken임 안에있는 principal에 CustomUserDetails들어있음

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        return jwtToken;
    }
    /*
        public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {}
        public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer {}
     */
}
