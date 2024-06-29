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
//    public void sendResetEmail(String email, String code){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("비밀번호 재설정 요청");
//        message.setText("비밀번호 재설정을 위해 다음 코드를 사용하세요:" + code);
//        log.info("Sending password reset email to: {}", email);
//        log.info("token: {}", code);
////        try {
////            log.info("Email message: {}", message);
////            mailSender.send(message); //여기가 문제인거 같음
////            log.info("Password reset email sent to: {}", email);
////        } catch (Exception e) {
////            log.error("Failed to send email", e);
////        }
//        try {
//            mailSender.send(message);
//            log.info("Password reset email sent to: {}", email);
//        } catch (MailException e) {
//            log.error("Failed to send email due to mail exception: ", e);
//        } catch (Exception e) {
//            log.error("Failed to send email due to unknown exception: ", e);
//        }
//    }
}
