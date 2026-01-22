package com.example.sns.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/all")
    public String aa() {
        return "hello world";
    }
}
