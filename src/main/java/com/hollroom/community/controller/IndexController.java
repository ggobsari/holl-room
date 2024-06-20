package com.hollroom.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class IndexController {

    @GetMapping("/list")
    public String list(){
        return "community/content/community_list.html";
    }

    @GetMapping("/test")
    public String test(){
        return "community/index.html";
    }

}
