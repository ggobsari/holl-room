package com.hollroom.user.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDTO {

    private Long id;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userNickname;

//    private MultipartFile userImage;

    private String userIntroduce;

    private String userPhoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;

    private String userGender;

    private String userLocation;

}

