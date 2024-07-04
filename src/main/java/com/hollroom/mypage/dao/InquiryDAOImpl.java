package com.hollroom.mypage.dao;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryDAOImpl implements InquiryDAO {

    private final InquiryRepository inquiryRepository;
    private final AttachFileRepository attachRepository;

    //문의글 목록
    public Page<InquiryEntity> findByUserId(Long userId, Pageable pageable) {
        return inquiryRepository.findByUserId(userId, pageable);
    }
    //==============================================================================================
    //문의글 업로드
    @Override
    public void saveInquiry(InquiryEntity inquiryEntity) throws  Exception {
        inquiryRepository.save(inquiryEntity);
    }
    //첨부파일 저장
    @Override
    public void saveAttachFile(AttachFileEntity fileEntity) {
        System.out.println(fileEntity);
        attachRepository.save(fileEntity);
    }
    //==============================================================================================
    //문의글 상세보기
    @Override
    public InquiryEntity findById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }
    //첨부파일 불러오기
    @Override
    public List<AttachFileEntity> findAttachmentsByPostId(Long postId, TabType tabType) {
        return attachRepository.findByPostIdAndTabType(postId, tabType);
    }
}
