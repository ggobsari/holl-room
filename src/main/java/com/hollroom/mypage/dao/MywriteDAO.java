package com.hollroom.mypage.dao;

import com.hollroom.community.domain.dto.CommunityPagingEntityDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.mypage.domain.dto.MywriteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MywriteDAO {
    //userId 글 목록
    Page<CommunityEntity> findByUserIdAndCategory(Long userId, String category, Pageable pageable);
    Page<CommunityEntity> findByUserId(Long userId, Pageable pageable);

}
