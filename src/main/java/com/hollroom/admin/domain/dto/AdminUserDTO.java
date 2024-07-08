package com.hollroom.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("admin_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
    private Long id;
    private Boolean is_deleted;
    private Boolean ban;

    private Date user_Signup_At;
    private Date user_Birthday;

    private String user_Email;
    private String user_Gender;
    private String user_Image;
    private String user_Introduce;
    private String user_Location;
    private String user_Name;
    private String user_Nickname;
    private String user_Password;
    private String user_Phone_Number;
    private String login_Type;
}
