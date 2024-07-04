package com.hollroom.mypage.dao;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InquiryDAO {
    //문의글 목록
    Page<InquiryEntity> findByUserId(Long userId, Pageable pageable);
    //문의글 업로드
    void saveInquiry(InquiryEntity inquiryEntity) throws Exception;
    //첨부파일저장
    public void saveAttachFile(AttachFileEntity fileEntity);
    //문의글 상세보기
    InquiryEntity findById(Long id);
    //첨부파일불러오기
    List<AttachFileEntity> findAttachmentsByPostId(Long postId, TabType tabType);
}
