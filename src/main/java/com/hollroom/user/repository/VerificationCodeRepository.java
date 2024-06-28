package com.hollroom.user.repository;

import com.hollroom.user.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    void deleteByUserPhoneNumber(String userPhoneNumber);
    Optional<VerificationCode> findByUserPhoneNumberAndCode(String userPhoneNumber, String code);
}
