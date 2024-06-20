package com.hollroom.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @GetMapping("/list")
    public String listPage(){
        return "community/content/community_list";
    }

    @GetMapping("/write")
    public String writePage(){
        return "community/content/community_write";
    }
    //임시
    @GetMapping("/read")
    public String test(){
        return "community/content/community_read";
    }

}
