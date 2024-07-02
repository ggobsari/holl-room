package com.hollroom.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsRequestDTO {
    private Long postId;
    private Long userId;
    private String comments;
    private Long commentId;

    public CommentsRequestDTO(Long postId, Long userId, String comments) {
        this.postId = postId;
        this.userId = userId;
        this.comments = comments;
    }
}
