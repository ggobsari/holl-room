package com.hollroom.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private String userIntroduce;

    @Column(unique = true)
    private String userPhoneNumber;

    private Date userBirthday;

    private String userGender;

    private String userLocation;

    private LocalDate userSignupAt;

    private Boolean userAdmin;

    private Boolean ban;

    private Boolean is_deleted;

    public UserEntity(String userEmail, String userPassword, String userName, String userNickname,
                      String userIntroduce, String userPhoneNumber, Date userBirthday,
                      String userGender, String userLocation, LocalDate userSignupAt, Boolean userAdmin,
                      Boolean ban, Boolean delete) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userIntroduce = userIntroduce;
        this.userPhoneNumber = userPhoneNumber;
        this.userBirthday = userBirthday;
        this.userGender = userGender;
        this.userLocation = userLocation;
        this.userAdmin = userAdmin;
        this.userSignupAt = userSignupAt;
        this.ban = ban;
        this.is_deleted = delete;
    }
}