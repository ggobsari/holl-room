package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoommateDAOImpl implements RoommateDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    @Override
    public int register(RoommateDTO board) {
        return sessionTemplate.insert("com.hollroom.roommate.insert", board);
    }

//    @Override
//    public int delete(int roommate_id) {
//        return 0;
//    }
//
//    @Override
//    public List<RoommateDTO> search(String keyword) {
//        return List.of();
//    }
//
//    @Override
//    public List<RoommateDTO> search(String tag, String keyword) {
//        return List.of();
//    }

    @Override
    public RoommateDTO select(int roommate_id) {
        return sessionTemplate.selectOne("com.hollroom.roommate.select", roommate_id);
    }

    @Override
    public List<RoommateDTO> getBoardList() {
        return sessionTemplate.selectList("com.hollroom.roommate.selectall");
    }
}
