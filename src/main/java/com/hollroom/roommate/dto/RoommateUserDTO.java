package com.hollroom.roommate.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.Date;

@Alias("rmmt_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoommateUserDTO {
    private Long id;
    private String user_email;
    private String user_password;
    private String user_name;
    private String user_nickname;
    private String user_image;
    private String user_introduce;
    private String user_phone_number;
    private Date user_birthday;
    private String user_gender;
    private String user_location;
    private LocalDate user_signup_at;
    private Boolean user_admin;
    private Boolean ban;
    private Boolean is_deleted;
}