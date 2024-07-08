package com.hollroom.mypage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("rmmt_board")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyRoommateDTO {
    private int roommate_id;
    private Long id;
    private String title;
    private String content;
    private Date created_at;
    private Date deleted_at;
    private Date updated_at;
}
