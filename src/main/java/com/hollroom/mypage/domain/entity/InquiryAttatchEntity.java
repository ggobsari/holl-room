package com.hollroom.mypage.domain.entity;

import com.hollroom.common.TabType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "InquiryAttach")
@Getter
@Setter
public class InquiryAttatchEntity {
    //첨부파일 테이블
    @Id
    @GeneratedValue
    private Long fileId; //첨부파일 아이디
    private String fileOriginalName; //원본파일명
    private String fileStoreName; //저장된 파일명

    private Long postId;

    public InquiryAttatchEntity(Long fileId, String fileOriginalName, String fileStoreName) {
        this.fileId = fileId;
        this.fileOriginalName = fileOriginalName;
        this.fileStoreName = fileStoreName;
    }

}
