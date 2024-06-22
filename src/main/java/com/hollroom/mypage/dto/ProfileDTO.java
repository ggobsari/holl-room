package com.hollroom.mypage.dto;

import com.hollroom.user.dto.UserDTO;
import com.hollroom.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProfileDTO extends UserDTO {

    private String userNickname;
    private String userImage;
    private Date userSignupAt;
    private String userPhoneNumber;
    private Date userBirthday;
    private Boolean userGender;
    private String userLocal;
    private Boolean userAdmin;
    private String userInfo;

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
        this.userLocal = userEntity.getUserLocal();
        this.userAdmin = userEntity.getUserAdmin();
        this.userInfo = userEntity.getUserInfo();
    }
}
