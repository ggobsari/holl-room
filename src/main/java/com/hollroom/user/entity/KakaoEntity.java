package com.hollroom.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class KakaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kakaoUserEmail;

//    @Column(nullable = false)
//    private String kakaoUserPassword;

    private String kakaoUserName;

    private String kakaoUserNickname;

    private String kakaoUserImage;

    private String kakaoUserIntroduce;

    private String kakaoUserPhoneNumber;

    private Date kakaoUserBirthday;

    private String kakaoUserGender;

    private String kakaoUserLocation;

    private LocalDate kakaoUserSignupAt;

    private Boolean kakaoUserAdmin;

    private Boolean kakaoUserBan;

    private Boolean kakaoUserIsDeleted;

}
