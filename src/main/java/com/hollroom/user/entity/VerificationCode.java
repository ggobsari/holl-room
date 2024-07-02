package com.hollroom.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class VerificationCode {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    private Long id;

    private String userPhoneNumber;

    private String code;

    private LocalDateTime expirationTime;

    public VerificationCode(String userPhoneNumber, String code, LocalDateTime expirationTime) {
        this.userPhoneNumber = userPhoneNumber;
        this.code = code;
        this.expirationTime = expirationTime;
    }
}
