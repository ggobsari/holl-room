package com.hollroom.community.domain.entity;


import com.hollroom.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community")
public class CommunityEntity {
    @Id
    @GeneratedValue
    private Long postId; //게시글 아이디
    private String title; // 게시글 제목
    private String content; //게시글 내용
    @CreationTimestamp
    private Date createdAt; //작성일(생성일)
    @UpdateTimestamp
    private Date updatedAt; //수정일
    private int viewCount; //조회수
    private String category; //카테고리
    private String deletedTrue; //게시글삭제여부
    private Date deletedAt; //게시글삭제일시

    //유저 Id FK (작성자)
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "postId")
//    private List<AttachFileEntity> attachFileEntities = new ArrayList<>();
//
//    //제목, 카테고리, 본문내용, 작성자, 조회수
//    public CommunityEntity(String title, String content, int viewCount, String category, UserEntity user) {
//        this.title = title;
//        this.content = content;
//        this.viewCount = viewCount;
//        this.category = category;
//        this.user = user;
//    }
//    //첨부파일이 있을 경우의 생성자
//    public CommunityEntity(String title, String content, int viewCount, String category, UserEntity user, List<AttachFileEntity> attachFileEntities) {
//        this.title = title;
//        this.content = content;
//        this.viewCount = viewCount;
//        this.category = category;
//        this.user = user;
//        this.attachFileEntities = attachFileEntities;
//    }
}
