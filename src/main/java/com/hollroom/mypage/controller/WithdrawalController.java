package com.hollroom.mypage.controller;

import com.hollroom.mypage.service.ProfileService;
import com.hollroom.user.dto.UserSignupDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@org.springframework.stereotype.Controller
@RequestMapping("/mypage")
public class WithdrawalController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("withdrawal")
    public String interest() {
        return "mypage/withdrawal";  //mypage/withdrawl페이지 반환
    }


    @PostMapping("/verifypassword")
    public String withdraw(HttpSession session) {
        // 세션에서 사용자 이메일을 가져옴
        String email = (String) session.getAttribute("email");

        // 이메일이 없으면 로그인 페이지로 리디렉션
        if (email == null) {
            return "redirect:/login"; // 로그인 페이지로 리디렉션
        }

        // 이메일을 사용하여 is_deleted를 TRUE로 설정
        boolean success = profileService.deleteUser(email);

        // 성공 여부에 따라 다른 페이지로 리디렉션
        if (success) {
            return "redirect:/login";
        } else {
            return "redirect:/withdrawal";
        }
    }
}

