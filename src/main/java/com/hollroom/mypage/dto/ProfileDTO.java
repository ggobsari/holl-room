package com.hollroom.mypage.dto;

import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProfileDTO extends UserSignupDTO {

    private String userNickname;
    private String userImage;
    private LocalDate userSignupAt;
    private String userPhoneNumber;
    private Date userBirthday;
    private String userGender;
    private String userLocation;
    private Boolean userAdmin;
    private String userIntroduce;

    public ProfileDTO(UserEntity userEntity) {
        this.setId(userEntity.getId());
        this.setUserEmail(userEntity.getUserEmail());
        this.setUserPassword(userEntity.getUserPassword());
        this.setUserName(userEntity.getUserName());
        this.userNickname = userEntity.getUserNickname();
        this.userImage = userEntity.getUserImage();
        this.userSignupAt = userEntity.getUserSignupAt();
        this.userPhoneNumber = userEntity.getUserPhoneNumber();
        this.userBirthday = userEntity.getUserBirthday();
        this.userGender = userEntity.getUserGender();
        this.userLocation = userEntity.getUserLocation();
        this.userAdmin = userEntity.getUserAdmin();
        this.userIntroduce = userEntity.getUserIntroduce();
    }
}
