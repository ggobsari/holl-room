package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
        return sessionTemplate.selectOne("com.hollroom.roommate.selectById", roommate_id);
    }

    @Override
    public List<RoommateDTO> selectAll(String userGender) {
        return sessionTemplate.selectList("com.hollroom.roommate.selectAll", userGender);
    }

    @Override
    public List<RoommateDTO> search(Map<String, String> data) {
        System.out.println("dao / category : " + data.get("category"));
        System.out.println("dao / searchword : " + data.get("searchword"));
        System.out.println("dao / usergender : " + data.get("usergender"));
//        return sessionTemplate.selectList("select * from board_roommate where user='밀로'");
//        List<RoommateDTO> list = null;
//        return list;
        return sessionTemplate.selectList("com.hollroom.roommate.search", data);
    }

    @Override
    public List<RoommateDTO> dynamicSearch(Map<String, Character> conditions) {
        System.out.println("############# daoImpl");
//        System.out.println("1 : " + conditions.get("habit1"));
//        System.out.println("2 : " + conditions.get("habit2"));
//        System.out.println("3 : " + conditions.get("habit3"));
        return sessionTemplate.selectList("com.hollroom.roommate.searchByConditions", conditions);
//        return null;
    }

    @Override
    public int delete(int roommate_id) {
        return sessionTemplate.delete("com.hollroom.roommate.delete", roommate_id);
    }

    @Override
    public RoommateUserDTO selectUser(int id) {
        return sessionTemplate.selectOne("com.hollroom.roommate.selectUser", id);
    }
}
