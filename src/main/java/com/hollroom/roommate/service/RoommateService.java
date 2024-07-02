package com.hollroom.roommate.service;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;

import java.util.List;

public interface RoommateService {
    int register(RoommateDTO board);
    int deleteBoard(int roommate_id);
    int editBoard(RoommateDTO board);
    RoommateDTO getBoard(int roommate_id);
    RoommateDTO getBoardById(int roommate_id);
    List<RoommateDTO> getBoardList();
    List<RoommateDTO> getSearchResult(String category, String searchWord);
    List<RoommateDTO> getFilteredResult(RoommateDTO conditions);
    RoommateUserDTO getUserInfo(int id);
}
