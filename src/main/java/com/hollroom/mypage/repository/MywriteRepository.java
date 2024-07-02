package com.hollroom.mypage.repository;

import com.hollroom.community.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MywriteRepository extends JpaRepository<CommunityEntity, Long> {
    Page<CommunityEntity> findByUserIdAndCategory(Long userId, String category, Pageable pageable);
    Page<CommunityEntity> findByUserId(Long userId, Pageable pageable);
}
