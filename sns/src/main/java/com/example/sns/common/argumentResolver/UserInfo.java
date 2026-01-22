package com.example.sns.common.argumentResolver;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    JWT 만들때 사용됨
    , 컨트롤러 매개변수로 받을때도 사용됨
    
    MiniUserDetails 과 매핑되는 객체 
 */
@Data
@AllArgsConstructor
public class UserInfo {
    private Long userId;
    private String userNickname;
}
