package com.hollroom.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString///////
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence") @SequenceGenerator(name = "user_sequence", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
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

    private LocalDate userSignupAt;

    private String loginType;

    private Boolean userAdmin;

    private Boolean ban;

    private Boolean isDeleted;

    private String resetToken;

    private Date isDeletedAt;

    public UserEntity(String userEmail, String userPassword, String userName, String userNickname,
                      String userIntroduce, String userPhoneNumber, Date userBirthday,
                      String userGender, String userLocation, String loginType, LocalDate userSignupAt, Boolean userAdmin,
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
        this.loginType = loginType;
        this.userAdmin = userAdmin;
        this.userSignupAt = userSignupAt;
        this.ban = ban;
        this.isDeleted = isDeleted;
    }
}