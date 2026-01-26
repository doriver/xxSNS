package com.example.sns.modules.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // 관리자 페이지
    @GetMapping("/admin-view")
    public String adminView() {
        return "user/admin";
    }
}
