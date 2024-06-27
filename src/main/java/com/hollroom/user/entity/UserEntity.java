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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence") @SequenceGenerator(name = "user_sequence", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true)
    private String userNickname;

    private String userImage;

    private String userIntroduce;

    @Column(unique = true)
    private String userPhoneNumber;

    @Column(nullable = false)
    private Date userBirthday;

    private String userGender;

    @Column(nullable = false)
    private String userLocation;

    private LocalDate userSignupAt;

    private Boolean userAdmin;

    private Boolean ban;

    private Boolean isDeleted;

    public UserEntity(String userEmail, String userPassword, String userName, String userNickname,
                      String userIntroduce, String userPhoneNumber, Date userBirthday,
                      String userGender, String userLocation, LocalDate userSignupAt, Boolean userAdmin,
                      Boolean ban, Boolean isDeleted) {
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
        this.isDeleted = isDeleted;
    }
}