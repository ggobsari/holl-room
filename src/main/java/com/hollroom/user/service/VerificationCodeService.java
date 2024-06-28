package com.hollroom.user.service;

import com.hollroom.user.entity.VerificationCode;
import com.hollroom.user.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeService {
    //================================================================================================================//
    private final VerificationCodeRepository verificationCodeRepository;
    //================================================================================================================//
    public SingleMessageSentResponse sendSMS(String userPhoneNumber) {
        log.info(userPhoneNumber);

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSBDJNL6CHPVPLN",
                "ML5C78U6YW9HBCLV4NLBXH11JPJXYZGY", "https://api.coolsms.co.kr");

        Message message = new Message();

        Random random = new Random();

        StringBuilder numString = new StringBuilder();

        for (int i = 0; i < 4; i++){
            numString.append(random.nextInt(10));
        }

        message.setFrom("01041728411");
        message.setTo(userPhoneNumber);
        message.setText("hollroom 휴대폰 인증 메세지 : 인증번호는" + "[" + numString + "]" + "입니다.");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info(String.valueOf(response));

        //기존 인증번호 삭제
        verificationCodeRepository.deleteByUserPhoneNumber(userPhoneNumber);

        //새 인증번호 저장
        VerificationCode verificationCode = new VerificationCode(
                userPhoneNumber,
                numString.toString(),
                LocalDateTime.now().plusMinutes(5)
        );

        verificationCodeRepository.save(verificationCode);

        return response;
    }
    //================================================================================================================//
    public boolean verifyCode(String userPhoneNumber, String code){
        LocalDateTime now = LocalDateTime.now();
        return verificationCodeRepository.findByUserPhoneNumberAndCode(userPhoneNumber, code)
                .filter(verificationCode -> now.isBefore(verificationCode.getExpirationTime()))
                .isPresent();
    }
    //================================================================================================================//
}
