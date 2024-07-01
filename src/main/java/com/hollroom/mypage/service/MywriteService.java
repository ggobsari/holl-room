package com.hollroom.mypage.service;


import com.hollroom.mypage.domain.dto.MywriteDTO;

import java.util.List;
import java.util.Map;

public interface MywriteService {
    //userId의 글 목록 불러오기
    Map<String, Object> getPostsByUserIdAndCategory(Long userId, String category, int page);
}
