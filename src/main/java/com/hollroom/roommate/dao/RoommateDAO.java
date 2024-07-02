package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;

import java.util.List;
import java.util.Map;

public interface RoommateDAO {
    int register(RoommateDTO board);
    int update(RoommateDTO board);
    RoommateDTO select(int roommate_id);
    RoommateDTO selectById(int roommate_id);
    List<RoommateDTO> selectAll();
    List<RoommateDTO> search(Map<String, String> data);
    List<RoommateDTO> dynamicSearch(Map<String, Character> conditions);
    int delete(int roommate_id);
    RoommateUserDTO selectUser(int id);
}
