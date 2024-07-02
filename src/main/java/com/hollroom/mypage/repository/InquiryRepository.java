package com.hollroom.mypage.repository;

import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<InquiryEntity,Long> {
    InquiryEntity findByPostId(Long postId);
    Page<InquiryEntity> findAll(Pageable pageable);

    Page<InquiryEntity> findByUserId(Long userId, Pageable pageable);
}
