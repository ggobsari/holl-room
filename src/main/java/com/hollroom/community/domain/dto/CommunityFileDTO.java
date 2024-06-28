package com.hollroom.community.domain.dto;

import com.hollroom.common.TabType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityFileDTO {
    private Long fileId; //첨부파일 아이디

    private TabType tabType; // 탭타입 - 커뮤니티, 룸메이트, 마이페이지...
    private String fileOriginalName; //원본파일명
    private String fileStoreName; //저장된 파일명
}
