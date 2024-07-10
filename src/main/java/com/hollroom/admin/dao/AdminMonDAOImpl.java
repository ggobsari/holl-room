package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminMonDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminMonDAOImpl implements AdminMonDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    @Override
    public List<AdminMonDTO> selectAllAdminMon() {
        return sessionTemplate.selectList("com.hollroom.admin.monthlyList");
    }

    @Override
    public void selectAdminMonById(AdminMonDTO adminMon) {
        sessionTemplate.selectOne("com.hollroom.admin.monthlyDeleted", adminMon);
    }
}
