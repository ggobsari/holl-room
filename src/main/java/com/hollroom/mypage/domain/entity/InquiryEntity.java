package com.hollroom.mypage.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inquiry")
public class InquiryEntity {
    @Id
    @GeneratedValue
    private Long postId; //게시글 아이디
    private String title; // 게시글 제목
    private String content; //게시글 내용
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss초", timezone="Asia/Seoul")
    private Date createdAt; //작성일(생성일)
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss초", timezone="Asia/Seoul")
    private Date updatedAt; //수정일
    private int viewCount; //조회수
    private String category; //카테고리
    private Date deletedAt; //게시글삭제일시
    private Date answerAt; //답변 일시
    private String answerContnet; //답변 내용

    //유저 Id FK (작성자)
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
}