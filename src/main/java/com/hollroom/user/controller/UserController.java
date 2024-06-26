package com.hollroom.user.controller;

import com.hollroom.user.dto.UserLoginDTO;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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
    //닉네임 중복 확인 요청
    @GetMapping("/checkNickname")
    public ResponseEntity<?> checkNickname(@RequestParam("userNickname") String userNickname){
        try {
            userService.isNicknameDuplicate(userNickname).orElseThrow(() -> new RuntimeException("사용 가능한 닉네임입니다,"));
            return ResponseEntity.ok().body("닉네임이 이미 사용 중입니다.");
        } catch (Exception e){
            return ResponseEntity.ok().body("사용 가능한 닉네임입니다.");
        }
    }
    //이메일 중복 확인
    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam("userEmail") String userEmail){
        try {
            userService.isEmailAlreadyExists(userEmail).orElseThrow(() -> new RuntimeException("사용 가능한 이메일입니다."));
            return ResponseEntity.ok().body("이미 사용 중인 이메일입니다.");
        } catch (Exception e){
            return ResponseEntity.ok().body("사용 가능한 이메일입니다.");
        }
    }
    //인증번호 확인
//    @GetMapping("/checkVerificationCode")
//    public ResponseEntity<?> checkVerificationCode(){
//        userService.sendSMS();
//    }
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
