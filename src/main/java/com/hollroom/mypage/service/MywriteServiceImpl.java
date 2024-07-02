package com.hollroom.mypage.service;

import com.hollroom.mypage.dao.MywriteDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MywriteServiceImpl implements MywriteService {

    @Autowired
    private MywriteDAO mywriteDAO;

    //userId의 글 목록 불러오기
    @Override
    public Map<String, Object> getPostsByUserIdAndCategory(Long userId, String category, int page) {
        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC,"updatedAt"));
        Map<String, Object> result = new HashMap<>();
        var postPage = mywriteDAO.findByUserIdAndCategory(userId, category, pageRequest);
        result.put("posts", postPage.getContent());
        result.put("totalPages", postPage.getTotalPages());
        return result;
    }
}



