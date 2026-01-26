package com.example.sns.modules.user.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignInDTO {
    //    @NotBlank(message = "닉네임을 입력해주세요.")
    //    @Size(min = 1, max = 15, message = "닉네임은 15자 이하로 입력하세요.")
    private String username;

    //    @NotBlank(message = "비밀번호를 입력해주세요.")
    //    @Size(min = 1, max = 15, message = "비밀번호는 20자 이하로 입력하세요.")
    private String password;
}
