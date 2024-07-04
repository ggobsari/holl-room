package com.hollroom.mypage.domain.dto;

import com.hollroom.common.TabType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryAttatchDTO {
    private Long fileId; //첨부파일 아이디

    private TabType tabType; // 탭타입 - 커뮤니티, 룸메이트, 마이페이지...
    private String fileOriginalName; //원본파일명
    private String fileStoreName; //저장된 파일명
}
