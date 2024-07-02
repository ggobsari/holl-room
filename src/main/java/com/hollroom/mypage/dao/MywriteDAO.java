package com.hollroom.mypage.dao;

import com.hollroom.community.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MywriteDAO {
    //userId 글 목록
    Page<CommunityEntity> findByUserIdAndCategory(Long userId, String category, Pageable pageable);

}
