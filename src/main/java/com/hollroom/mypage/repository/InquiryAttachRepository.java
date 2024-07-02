package com.hollroom.mypage.repository;

import com.hollroom.mypage.domain.entity.InquiryAttatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryAttachRepository extends JpaRepository<InquiryAttatchEntity,Long> {
    List<InquiryAttatchEntity> findByPostId(Long postId);
}
