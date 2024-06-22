package com.hollroom.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userEmail;

    private String userPassword;

    private String userName;

    @Column(unique = true)
    private String userNickname;

    private String userImage;

    private Date userSignupAt;

    @Column(unique = true)
    private String userPhoneNumber;

    private Date userBirthday;

    private Boolean userGender;

    private String userLocal;

    private Boolean userAdmin;

    private String userInfo;

//    public UserEntity(UserDTO userDTO) {
//        this.userEmail = userDTO.getUserEmail();
//        this.userPassword = userDTO.getUserPassword();
//        this.userName = userDTO.getUserName();
//    }

    public UserEntity(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
