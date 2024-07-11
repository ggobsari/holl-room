package com.hollroom.mypage.service;

import com.hollroom.admin.domain.dto.AdminComDTO;
import com.hollroom.community.domain.dto.CommunityPagingDTO;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;

import java.util.List;
import java.util.Map;

public interface MywriteService {
    //모든 게시글 목록 보기
    List<AdminComDTO> myCommunityList(Long id);
    //카테고리별 게시글 목록 보기
    List<AdminComDTO> myCommunityListCategory(AdminComDTO adminComDTO);
    //Roommate userId의 글 목록 불러오기
    List<MyRoommateDTO> selectMyRoommatesByUserId(Long userId);
}
