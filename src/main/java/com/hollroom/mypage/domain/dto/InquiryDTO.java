package com.hollroom.mypage.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;  // List 인터페이스를 사용하기 위해 추가

import java.util.Date;

@Setter
@Getter
public class InquiryDTO {
    private String category;
    private String title;
    private String content;
    private Long userId;

    private Long postId;
    private LocalDateTime createdAt;
    private Date answeredAt;
    private boolean answered;
    private String answerContent;

    private List<InquiryAttatchDTO> files;

}
