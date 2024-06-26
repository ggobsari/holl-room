package com.hollroom.user.controller;

import com.hollroom.user.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    //휴대폰 문자 인증 전송
    @PostMapping("/sendSMS")
    public SingleMessageSentResponse sendSMS(@RequestParam("userPhoneNumber") String userPhoneNumber){
        log.info(userPhoneNumber);
        return verificationCodeService.sendSMS(userPhoneNumber);
    }

    //인증 번호 확인
    @PostMapping("/verify")
    public boolean verifyCode(@RequestParam("userPhoneNumber") String userPhoneNumber,
                              @RequestParam("verifyCode") String code){
        return verificationCodeService.verifyCode(userPhoneNumber, code);
    }

}
