package com.hollroom.mypage.controller;

import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage")
public class Controller {
    // 세션 체크 헬퍼 메서드
    private boolean checkSession(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        return user != null;
    }

    @GetMapping("/mywrite")
    public String mywrite(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/mywrite"; //write뷰 반환
    }

    @GetMapping("/interest")
    public String interest(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/interest"; //interest 반환
    }

    @GetMapping("/inquiry")
    public String inquiry(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/inquiry"; //inquiry 반환
    }

    @GetMapping("/inquiry_write")
    public String inquiry_write(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/inquiry_write"; //inquiry_write 반환
    }

    @GetMapping("/inquiry_content")
    public String inquiry_content(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/inquiry_content"; //inquiry_content 반환
    }

    @GetMapping("/profile_test")
    public String profile_test() {
        return "mypage/profile_test"; //profile_test 뷰
    }
    
}
