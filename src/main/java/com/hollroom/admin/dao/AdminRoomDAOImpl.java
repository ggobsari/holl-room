package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminRoomDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRoomDAOImpl implements AdminRoomDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    //룸메이트 목록
    @Override
    public List<AdminRoomDTO> selectAllAdminRoommates() {
        return sessionTemplate.selectList("com.hollroom.admin.roommateList");
    }

    //룸메이트 글 삭제/취소
    @Override
    public void selectAdminRoommateById(AdminRoomDTO adminRoom) {
        sessionTemplate.selectOne("com.hollroom.admin.roommateDeleted", adminRoom);
    }
}
