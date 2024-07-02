package com.hollroom.community.domain.dto;

import com.hollroom.community.domain.entity.CommentsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsResponseDTO {

    private Long commentId;
    private String comments;
    private Date updateAt;
    private Long postId; //게시글 아이디
    private Long userId;

    private String nickName; //유저 아이디 대신에 보여줄 값(닉네임)


    //.map에서 사용할 생성자 작성
    public CommentsResponseDTO(CommentsEntity commentsEntity) {
        this.commentId = commentsEntity.getCommentId();
        this.comments = commentsEntity.getComments();
        this.updateAt = commentsEntity.getUpdateAt();
        this.postId = commentsEntity.getPostId();
        this.userId = commentsEntity.getUserId();
    }
}
