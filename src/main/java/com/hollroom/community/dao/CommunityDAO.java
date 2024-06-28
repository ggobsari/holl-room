package com.hollroom.community.dao;

import com.hollroom.community.domain.dto.CommunityPagingEntityDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;

import java.util.List;

public interface CommunityDAO {
    //게시글등록
    void insert(CommunityEntity entity);
    //첨부파일 등록
    void insertFile(List<AttachFileEntity> fileEntityList);
    //페이징처리된 모든 게시글 목록 보기
    CommunityPagingEntityDTO pagingFindAll(String page);

    //페이징처리된 카테고리별 게시글 목록 보기
    CommunityPagingEntityDTO pagingFindCategory(String category, String page);

    CommunityEntity findById(Long postId);

    List<AttachFileEntity> getFiles(Long postId);

    //조회수 변경
    void updateViewCounting(Long postId);

    //게시글 수정
    void update(CommunityEntity entity);
}
