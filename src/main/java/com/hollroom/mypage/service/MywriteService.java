package com.hollroom.mypage.service;

import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import com.hollroom.mypage.domain.dto.MywriteDTO;
import com.hollroom.roommate.dto.RoommateDTO;

import java.util.List;
import java.util.Map;

public interface MywriteService {
    //Community userId의 글 목록 불러오기
    Map<String, Object> getPostsByUserIdAndCategory(Long userId, String category, int page);
    //Roommate userId의 글 목록 불러오기
    List<MyRoommateDTO> selectMyRoommatesByUserId(Long userId);
}
