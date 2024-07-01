package com.hollroom.mypage.dao;

import com.hollroom.community.domain.dto.CommunityPagingEntityDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.community.repository.CommunityRepository;
import com.hollroom.mypage.domain.dto.MywriteDTO;
import com.hollroom.mypage.repository.MywriteRepository;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MywriteDAOImpl implements MywriteDAO {
    @Autowired
    private MywriteRepository mywriteRepository;

    @Override
    public Page<CommunityEntity> findByUserIdAndCategory(Long userId, String category, Pageable pageable) {
        if ("all".equals(category)) {
            return mywriteRepository.findByUserId(userId, pageable);
        } else {
            return mywriteRepository.findByUserIdAndCategory(userId, category, pageable);
        }
    }

    @Override
    public Page<CommunityEntity> findByUserId(Long userId, Pageable pageable) {
        return mywriteRepository.findByUserId(userId, pageable);
    }
}
