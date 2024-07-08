package com.hollroom.admin.service;

import com.hollroom.admin.dao.InquiryListDAO;
import com.hollroom.admin.domain.dto.InquiryListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryListServiceImpl implements InquiryListService {

    private final InquiryListDAO inquiryListDAO;

    //문의하기 전체 목록보기
    @Override
    public List<InquiryListDTO> selectInquiry() {
        return inquiryListDAO.selectInquiry();
    }
    //문의하기 글 전체 개수
    @Override
    public Long selectInquiryCount() {
        return inquiryListDAO.selectInquiryCount();
    }
    //문의하기 답변 개수
    @Override
    public Long selectInquiryAnswerCount() {
        return inquiryListDAO.selectInquiryAnswerCount();
    }

    //문의하기 상세 보기
    @Override
    public InquiryListDTO selectInquiryByPostId(Long postId) {
        return inquiryListDAO.selectInquiryByPostId(postId);
    }
    //문의하기 답변 등록
    @Override
    public void updateInquiryAnswer(InquiryListDTO inquiry) {
        inquiryListDAO.updateInquiryAnswer(inquiry);
    }
}
