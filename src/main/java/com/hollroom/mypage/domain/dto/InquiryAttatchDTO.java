package com.hollroom.mypage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InquiryAttatchDTO {
    private Long id;
    private String fileName;
    private String filePath;
}
