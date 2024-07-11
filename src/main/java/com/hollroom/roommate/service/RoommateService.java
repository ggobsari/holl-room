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
    List<RoommateDTO> getBoardList(String userGender);
    List<RoommateDTO> getSearchResult(String category, String searchWord, String userGender);
    List<RoommateDTO> getFilteredResult(RoommateDTO conditions, String userGender);
    RoommateUserDTO getUserInfo(int id);
}
