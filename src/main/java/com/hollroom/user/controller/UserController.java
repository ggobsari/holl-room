package com.hollroom.user.controller;

import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //restcontroller는 데이터를 리턴, 뷰를 리턴하려면 controller로 변환
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입 페이지 출력 요청
    @GetMapping("/signup")
    public String signupForm(){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupDTO userSignupDTO){
        userService.signup(userSignupDTO);
        return "user/login";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserSignupDTO userSignupDTO, HttpSession session){
        String loginResult = userService.login(userSignupDTO);
        if(loginResult != null){
            session.setAttribute("loginEmail", "로그인 성공");
            return "/main";
        } else{
            return "/login";
        }
    }
}
