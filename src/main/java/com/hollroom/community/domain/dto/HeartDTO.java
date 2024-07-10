package com.hollroom.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeartDTO {
    private Long heartId;
    private Long userId;
    private Long postId;
    private String checkHeart;
}
