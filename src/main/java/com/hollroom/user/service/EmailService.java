package com.hollroom.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    //================================================================================================================//
    private final JavaMailSender mailSender;
    //================================================================================================================//
    public void sendResetEmail(String email, String code) {
        String subject = "비밀번호 재설정 요청";
        String body = "비밀번호 재설정을 위해 다음 코드를 사용하세요:"
                + code;

        sendEmail(email, subject, body);
    }
    //================================================================================================================//
    public void sendEmail(String email, String subject, String body){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(email);
            log.info(email);
            helper.setSubject(subject);
            log.info(subject);
            helper.setText(body, true);
            log.info(body);
            mailSender.send(message);
            log.info("이메일을 성공적으로 보냈습니다. 수신자: {}", email);
        } catch (MessagingException | MailException e) {
            log.error("이메일을 보내는 중 오류가 발생했습니다. 수신자: {}", email, e);
            throw new RuntimeException("이메일 전송 중 오류 발생", e);
        }
    }
}
