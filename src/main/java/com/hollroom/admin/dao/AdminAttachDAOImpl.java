package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminAttachDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminAttachDAOImpl implements AdminAttachDAO {
    private final SqlSessionTemplate sessionTemplate;

    @Override
    public List<AdminAttachDTO> getAdminAttach(Long postId) {
        return sessionTemplate.selectList("com.hollroom.admin.attachFileById", postId);
    }
}
