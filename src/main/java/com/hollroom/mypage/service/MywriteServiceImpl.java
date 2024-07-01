package com.hollroom.mypage.service;

import com.hollroom.community.domain.dto.*;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.exception.UsernameNotFoundException;
import com.hollroom.mypage.dao.MywriteDAO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.dto.MywriteDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import com.jayway.jsonpath.internal.Path;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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



