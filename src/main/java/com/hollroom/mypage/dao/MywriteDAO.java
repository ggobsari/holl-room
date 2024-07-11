package com.hollroom.mypage.dao;

import com.hollroom.admin.domain.dto.AdminComDTO;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;

import java.util.List;

public interface MywriteDAO {
    //모든 게시글 목록 보기
    List<AdminComDTO> myCommunityList(Long id);
    //카테고리별 게시글 목록 보기
    List<AdminComDTO> myCommunityListCategory(AdminComDTO adminComDTO);

    List<MyRoommateDTO> selectMyRoommateById(Long id);
}
