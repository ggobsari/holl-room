package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;

import java.util.List;

public interface RoommateDAO {
    int register(RoommateDTO board);
    int update(RoommateDTO board);
    RoommateDTO select(int roommate_id);
    RoommateDTO selectById(int roommate_id);
    List<RoommateDTO> getBoardList();
    int delete(int roommate_id);
    RoommateUserDTO selectUser(int id);
}
