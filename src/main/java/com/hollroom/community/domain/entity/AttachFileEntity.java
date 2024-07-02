package com.hollroom.community.domain.entity;

import com.hollroom.common.TabType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachFile")
public class AttachFileEntity {
    //첨부파일 테이블
    @Id
    @GeneratedValue
    private Long fileId; //첨부파일 아이디
    private TabType tabType; // 탭타입 - 커뮤니티, 룸메이트, 마이페이지...
    private String fileOriginalName; //원본파일명
    private String fileStoreName; //저장된 파일명

    private Long postId;

    public AttachFileEntity(Long postId, TabType tabType, String fileOriginalName, String fileStoreName) {
        this.postId = postId;
        this.tabType = tabType;
        this.fileOriginalName = fileOriginalName;
        this.fileStoreName = fileStoreName;
    }
}
