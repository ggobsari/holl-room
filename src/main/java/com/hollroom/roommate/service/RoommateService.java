package com.hollroom.roommate.service;

import com.hollroom.roommate.dto.RoommateDTO;

import java.util.List;

public interface RoommateService {
    int register(RoommateDTO board);
//    int delete(int roommate_id);
//    List<RoommateDTO> search(String keyword);
//    List<RoommateDTO> search(String tag, String keyword);
    RoommateDTO getBoard(int roommate_id);
    List<RoommateDTO> getBoardList();
}
