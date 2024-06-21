package com.hollroom.mypage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/mypage")
public class Controller {
    @GetMapping("/mywrite")
    public String mywrite() {
        return "mypage/mywrite";  // mypage/mywrite.html 뷰를 반환
    }

    @GetMapping("/interest")
    public String interest() {
        return "mypage/interest";  // mypage/interest.html 뷰를 반환
    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "mypage/inquiry";  // mypage/inquiry.html 뷰를 반환
    }

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "mypage/withdrawal";  // mypage/withdrawal.html 뷰를 반환
    }
}
