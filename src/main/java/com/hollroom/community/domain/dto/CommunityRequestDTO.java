package com.hollroom.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityRequestDTO {
    //제목, 작성자, 카테고리, 첨부파일, 본문내용
    private String title;
    private Long id; //작성자
    private String category;
    private String content;
    private int viewCount;
    private List<MultipartFile> files;

    private Long postId;

    public List<MultipartFile> getFiles(){
        return files;
    }
}
