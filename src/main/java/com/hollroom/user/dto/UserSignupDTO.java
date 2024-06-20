package com.hollroom.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignupDTO {

    private Long id;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userNickname;

    private String userImage;

    private String userIntroduce;

    private String userPhoneNumber;

    private Date userBirthday;

    private String userGender;

    private String userLocation;

}
