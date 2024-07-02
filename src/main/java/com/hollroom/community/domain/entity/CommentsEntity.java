package com.hollroom.community.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue
    private Long commentId;
    private String comments;
    @CreationTimestamp
    private Date createAt;
    @UpdateTimestamp
    private Date updateAt;
    private String deletedCom; //댓글 삭제 여부
    private Date deleteAt;

    private Long postId; //게시글 아이디
    private Long userId; //유저 아이디

    public CommentsEntity(String comments, Long postId, Long userId) {
        this.comments = comments;
        this.postId = postId;
        this.userId = userId;
    }
}
