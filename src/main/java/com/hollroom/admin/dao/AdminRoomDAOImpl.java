package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminRoomDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public long countByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);

        Map<String, Timestamp> params = new HashMap<>();
        params.put("startTimestamp", startTimestamp);
        params.put("endTimestamp", endTimestamp);

        return sessionTemplate.selectOne("com.hollroom.admin.countByDate", params);
    }
}
