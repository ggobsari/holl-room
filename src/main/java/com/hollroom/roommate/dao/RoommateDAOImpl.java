package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
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

    @Override
    public int update(RoommateDTO board) {
        return sessionTemplate.update("com.hollroom.roommate.update", board);
    }

    @Override
    public RoommateDTO select(int roommate_id) {
        return sessionTemplate.selectOne("com.hollroom.roommate.select", roommate_id);
    }

    @Override
    public RoommateDTO selectById(int roommate_id) {
        return sessionTemplate.selectOne("com.hollroom.roommate.selectbyid", roommate_id);
    }

    @Override
    public List<RoommateDTO> getBoardList() {
        return sessionTemplate.selectList("com.hollroom.roommate.selectall");
    }

    @Override
    public int delete(int roommate_id) {
        return sessionTemplate.delete("com.hollroom.roommate.delete", roommate_id);
    }

    @Override
    public RoommateUserDTO selectUser(int id) {
        return sessionTemplate.selectOne("com.hollroom.roommate.selectuser", id);
    }
}
