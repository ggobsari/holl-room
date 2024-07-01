package com.hollroom.mypage.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MywriteDTO {
    private Long postId; //게시글 아이디
    private String title; //게시글 제목
    private String category; //게시글 카테고리
    private String updatedAt; //작성일(등록일)
    private int viewCount; //조회수

    private Long userId; //작성자 Id
}
