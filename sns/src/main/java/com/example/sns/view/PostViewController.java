package com.example.sns.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {

    @GetMapping("/post-view")
    public String postView() {

        return "postTimeline";
    }
}
