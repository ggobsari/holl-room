package com.hollroom.mypage.service;

import com.hollroom.mypage.domain.dto.InquiryAttatchDTO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface InquiryService {
    //문의글 목록
    Map<String, Object> getInquiriesByUserId(Long userId, int page);
    //문의글 업로드
    InquiryEntity saveInquiry(InquiryDTO inquiryDTO) throws Exception;
    //첨부파일 업로드
    void saveInquiryFile(List<MultipartFile> files, Long postId) throws Exception;

    //문의글 상세보기
    InquiryDTO getInquiryById(Long id);
    //첨부파일 불러오기
    List<InquiryAttatchDTO> getAttachmentsByPostId(Long postId);
}
