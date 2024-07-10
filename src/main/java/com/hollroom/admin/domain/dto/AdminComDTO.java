package com.hollroom.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("admin_community")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminComDTO {
    private String category;
    private String content;
    private String title;
    private String deleted;

    private Date created_At;
    private Date deleted_At;
    private Date updated_At;

    private Long post_Id;
    private Long user_Id;
    private Long view_Count;

    private String user_Nickname;
}
