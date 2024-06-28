package com.hollroom.community.repository;

import com.hollroom.community.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityEntity,Long> {
    Page<CommunityEntity> findByCategory(String category, Pageable pageRequest);
//    Page<CommunityEntity> findByCategoryAndTitleContaining(String category, String title, Pageable pageRequest);
    CommunityEntity findByPostId(Long postId);

}
