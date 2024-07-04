package com.hollroom.user.controller;

import com.hollroom.user.dto.ForgotPasswordRequestDTO;
import com.hollroom.user.dto.ResetPasswordRequestDTO;
import com.hollroom.user.dto.VerifyCodeRequestDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.service.EmailService;
import com.hollroom.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EmailController {
    //================================================================================================================//
    private final EmailService emailService;

    private final UserService userService;
    //================================================================================================================//
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        Optional<UserEntity> user = userService.findByUserEmail(forgotPasswordRequestDTO.getEmail());
        if (user.isPresent()) {
            log.info(String.valueOf(user.get().getUserEmail()));
            String code = userService.createPasswordResetToken(user.get());
            emailService.sendResetEmail(user.get().getUserEmail(), code);
            log.info(code);
            return "비밀번호 변경 요청 이메일이 발신되었습니다.";
        } else {
            return "찾을 수 없는 이메일입니다.";
        }
    }

    @PostMapping("/verifyCode")
    public String verifyCode(@RequestBody VerifyCodeRequestDTO verifyCodeRequestDTO) {
        if (userService.verifyResetToken(verifyCodeRequestDTO.getCode())) {
            return "이메일 확인 완료.";
        } else {
            return "유효하지 않은 코드입니다.";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        if (userService.findByUserEmail(resetPasswordRequestDTO.getEmail()).isPresent()){
            userService.updatePassword(resetPasswordRequestDTO.getEmail(), resetPasswordRequestDTO.getNewPassword());
            log.info(resetPasswordRequestDTO.getEmail());
            log.info(resetPasswordRequestDTO.getNewPassword());
            return "비밀번호 변경 완료";
        } else{
            return "가입되어 있는 회원이 아닙니다.";
        }
    }
}
