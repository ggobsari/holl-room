package com.hollroom.roommate.service;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;

import java.util.List;

public interface RoommateService {
    int register(RoommateDTO board);
    int deleteBoard(int roommate_id);
    int editBoard(RoommateDTO board);
//    List<RoommateDTO> search(String keyword);
//    List<RoommateDTO> search(String tag, String keyword);
    RoommateDTO getBoard(int roommate_id);
    RoommateDTO getBoardById(int roommate_id);
    List<RoommateDTO> getBoardList();
    RoommateUserDTO getUserInfo(int id);
}
