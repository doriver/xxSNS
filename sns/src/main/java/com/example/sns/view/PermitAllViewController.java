package com.example.sns.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PermitAllViewController {

    // 홈 화면(로그인, 회원가입 화면)
    @GetMapping("/sign-view")
    public String signView() {
        return "sign";
    }
}
