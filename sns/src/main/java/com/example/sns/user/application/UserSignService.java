package com.example.sns.user.application;


import com.example.sns.config.security.jwt.JwtToken;
import com.example.sns.config.security.jwt.JwtTokenProvider;
import com.example.sns.user.domain.entity.Role;
import com.example.sns.user.domain.entity.User;
import com.example.sns.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /*
        회원가입
    */
    public void registerUser(String nickname, String password, Role role) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptPassword = passwordEncoder.encode(password);

        if(encryptPassword.equals("")) {
            throw new RuntimeException("비밀번호 암호화 실패");
        }
        User user = User.builder()
                .nickname(nickname).password(encryptPassword).role(role)
                .build();

        userRepository.save(user);
    }

    // 로그인
    public JwtToken authenticateUser(String nickname, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nickname, password);

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
