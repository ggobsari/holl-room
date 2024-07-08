package com.hollroom.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("admin_roommate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoomDTO {
    private Date created_At;
    private Date updated_At;
    private Date deleted_At;

    private Long id;
    private Long roommate_Id;

    private String char_Room_Id;
    private String content;
    private String user_Nickname;
    private String title;

    private String deleted;
}
