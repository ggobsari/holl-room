package com.hollroom.admin.domain.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("inquiry_board")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListDTO {
    private Date answer_At;
    private String answer_Content;
    private String category;
    private String content;
    private Date created_At;
    private Date deleted_At;
    private Long post_Id;
    private String title;
    private Long user_Id;
}
