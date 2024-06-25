package com.hollroom.user.dto;

import com.hollroom.user.entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    private String userImage;

    private String userIntroduce;

    private String userPhoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;

    private String userGender;

    private String userLocation;

    private LocalDate userSignupAt;

    private Boolean userAdmin;

    private Boolean ban;

    private Boolean is_deleted;


    public UserSignupDTO(UserEntity userEntity) {
        this.id = id;
        this.userEmail = userEntity.getUserEmail();
        this.userPassword = userEntity.getUserPassword();
        this.userName = userEntity.getUserName();
        this.userNickname = userEntity.getUserNickname();
        this.userImage = userEntity.getUserImage();
        this.userIntroduce = userEntity.getUserIntroduce();
        this.userPhoneNumber = userEntity.getUserPhoneNumber();
        this.userBirthday = userEntity.getUserBirthday();
        this.userGender = userEntity.getUserGender();
        this.userLocation = userEntity.getUserLocation();
        this.userSignupAt = userEntity.getUserSignupAt();
        this.userAdmin = userEntity.getUserAdmin();

    }
}

