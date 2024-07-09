package com.hollroom.community.repository;

import com.hollroom.community.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityEntity,Long> {
    Page<CommunityEntity> findByCategoryAndDeleted(String category,String deleted,Pageable pageRequest);
    Page<CommunityEntity> findByDeleted(String deleted,Pageable pageRequest);
//    Page<CommunityEntity> findByCategoryAndTitleContaining(String category, String title, Pageable pageRequest);

    CommunityEntity findByPostId(Long postId);
    List<CommunityEntity> findByCategoryAndContentContaining(String category,String content);
    List<CommunityEntity> findByContentContaining(String content);

    //그런데 삭제된 글은 불러오지 않도록 하기
    //조건 검색
    Page<CommunityEntity> findByDeletedAndTitleContaining(String deleted,String title,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndContentContaining(String deleted,String content,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndUserId(String deleted,Long userId,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndTitleContainingOrContentContaining(String deleted,String title,String content,Pageable pageRequest);

    //카테고리 포함 조건검색
    Page<CommunityEntity> findByDeletedAndCategoryAndTitleContaining(String deleted,String category,String title,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndCategoryAndContentContaining(String deleted,String category,String content,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndCategoryAndUserId(String deleted,String category,Long userId,Pageable pageRequest);
    Page<CommunityEntity> findByDeletedAndCategoryAndTitleContainingOrContentContaining(String deleted,String category,String title,String content,Pageable pageRequest);

    //상위 조회수 3개
    List<CommunityEntity> findTop3ByOrderByViewCountDesc();
}
