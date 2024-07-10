package com.hollroom.mypage.dao;

import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MywriteDAO {
    //Community userId 글 목록
    Page<CommunityEntity> findByUserIdAndCategory(Long userId, String category, Pageable pageable);
    //Roommate userId 글 목록
    List<MyRoommateDTO> selectMyRoommateById(Long id);
}
