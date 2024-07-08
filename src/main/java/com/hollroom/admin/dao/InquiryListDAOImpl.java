package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.InquiryListDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryListDAOImpl implements InquiryListDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

//    문의하기 전체 목록
    @Override
    public List<InquiryListDTO> selectInquiry() {
        return sessionTemplate.selectList("com.hollroom.admin.inquiryList");
    }
    //문의하기 글 전체 개수
    @Override
    public Long selectInquiryCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.inquiryCount");
    }
    //문의하기 답변 개수
    @Override
    public Long selectInquiryAnswerCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.inquiryAnswerCount");
    }

    //문의하기 글 상세보기
    @Override
    public InquiryListDTO selectInquiryByPostId(long postId) {
        return sessionTemplate.selectOne("com.hollroom.admin.inquiryByPostId", postId);
    }

    //문의하기 답변등록
    @Override
    public void updateInquiryAnswer(InquiryListDTO inquiry) {
        sessionTemplate.update("com.hollroom.admin.inquiryAnswerRegister", inquiry);
    }
}
