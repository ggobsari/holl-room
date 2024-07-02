package com.hollroom.community.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hollroom.community.domain.entity.CommunityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityResponseDTO {
    //글번호, 작성자, 제목, 카테고리, 날짜, 조회수
    private Long postId; //글번호 (게시글아이디)
    private String title;
    //User테이블의 값을 받기 위해서
    private Long id;
    private String userNickname; //작성자, 원래는 id값을 가져오려고 했으나 사용자를 쉽게 이해하도록 닉네임으로 매핑
    private String category;
    private String content;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss초", timezone="Asia/Seoul")
    private Date updatedAt; //수정일
    private Date createdAt;
    private int viewCount;




//    private List<MultipartFile> files;

    //.map에서 사용할 생성자? 작성
    public CommunityResponseDTO(CommunityEntity communityEntity) {
        this.postId = communityEntity.getPostId();
        this.title = communityEntity.getTitle();
        this.id = communityEntity.getUser().getId();
        this.userNickname = communityEntity.getUser().getUserNickname();
        this.category = communityEntity.getCategory();
        this.content = communityEntity.getContent();
        this.updatedAt = communityEntity.getUpdatedAt();
        this.viewCount = communityEntity.getViewCount();
    }


}
