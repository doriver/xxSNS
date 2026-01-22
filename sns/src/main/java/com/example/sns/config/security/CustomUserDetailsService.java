package com.example.sns.config.security;

import com.example.sns.user.domain.entity.User;
import com.example.sns.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
    로그인 할때 사용됨
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(username);

        if (user == null) {
            // UsernameNotFoundException을 명확히 던져주는 것이 보안상 안전하며, Spring Security의 인증 실패 프로세스가 정상 작동
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }


        List<GrantedAuthority> authorities
                = user.getRole().getHierarchyAuthorities()  // Enum에 정의된 계층 구조를 통해 모든 권한을 가져옴
                                        .stream().map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
//        String role = user.getRole().toString();
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role));

        return new CustomUserDetails(user,authorities);
    }
}
