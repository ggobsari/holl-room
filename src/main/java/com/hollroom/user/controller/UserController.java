package com.hollroom.user.controller;

import com.hollroom.user.dto.UserLoginDTO;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //restcontroller는 데이터를 리턴, 뷰를 리턴하려면 controller로 변환
@Slf4j
@RequiredArgsConstructor
public class UserController {
    //================================================================================================================//
    private final UserService userService;
    //================================================================================================================//
    //회원가입 페이지 요청
    @GetMapping("/signup")
    public String signupForm(){
        return "user/signup";
    }
    //회원가입 요청
    @PostMapping("/signup")
    public String signup(UserSignupDTO userSignupDTO){
        System.out.println("============"+userSignupDTO);
//        log.info(userSignupDTO.toString());
        userService.signup(userSignupDTO);
        return "user/login";
    }
    //로그인 페이지 요청
    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }
    //로그인 요청
    @PostMapping("/login")
    public String login(UserLoginDTO userLoginDTO, HttpServletRequest request){
        log.info(userLoginDTO.getUserEmail());
        userService.login(userLoginDTO, request);
        return "index";
    }
    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        if(session == null){
            log.info("세션 없음");
        } else{
            log.info("세션 있음");
        }

        return "user/login";
    }
}
