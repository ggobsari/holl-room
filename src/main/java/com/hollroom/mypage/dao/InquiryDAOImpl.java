package com.hollroom.mypage.dao;

import com.hollroom.mypage.domain.entity.InquiryAttatchEntity;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.repository.InquiryAttachRepository;
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
    private final InquiryAttachRepository attachRepository;

    //문의글 목록
    public Page<InquiryEntity> findAll(Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }
    //==============================================================================================
    //문의글 업로드
    @Override
    public void saveInquiry(InquiryEntity inquiryEntity) throws  Exception {
        inquiryRepository.save(inquiryEntity);
    }
    //첨부파일 저장
    @Override
    public void saveAttachFile(InquiryAttatchEntity fileEntity) {
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
    public List<InquiryAttatchEntity> findAttachmentsByPostId(Long postId) {
        return attachRepository.findByPostId(postId);
    }
}
