package com.hollroom.user.controller;

import com.hollroom.user.dto.UserDTO;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입 페이지 출력 요청
    @GetMapping("/signup")
    public String signupForm(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDTO userDTO){
        log.info(userDTO.getUserEmail());
        log.info(userDTO.getUserPassword());
        log.info(userDTO.getUserName());
        userService.signup(userDTO);
        return "login";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO, HttpSession session){
        String loginResult = userService.login(userDTO);
        if(loginResult != null){
            session.setAttribute("loginEmail", "로그인 성공");
            return "main";
        } else{
            return "login";
        }
    }
}
