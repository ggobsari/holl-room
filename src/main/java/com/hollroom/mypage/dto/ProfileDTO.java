package com.hollroom.mypage.dto;

import com.hollroom.user.dto.UserDTO;
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
}
