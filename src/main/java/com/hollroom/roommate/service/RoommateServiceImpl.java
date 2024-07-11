package com.hollroom.roommate.service;

import com.hollroom.roommate.dao.RoommateDAO;
import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@Transactional
@RequiredArgsConstructor
public class RoommateServiceImpl implements RoommateService {
    @Autowired
    private RoommateDAO dao;

    @Override
    public int register(RoommateDTO board) {
        return dao.register(board);
    }

    @Override
    public int deleteBoard(int roommate_id) {
        return dao.delete(roommate_id);
    }

    @Override
    public int editBoard(RoommateDTO board) {
        return dao.update(board);
    }

    @Override
    public RoommateDTO getBoard(int roommate_id) {
        return dao.select(roommate_id);
    }

    @Override
    public RoommateDTO getBoardById(int roommate_id) {
        return dao.selectById(roommate_id);
    }

    @Override
    public List<RoommateDTO> getBoardList(String userGender) {
        return dao.selectAll(userGender);
    }

    @Override
    public List<RoommateDTO> getSearchResult(String category, String searchWord, String userGender) {
        Map<String, String> data = new HashMap<>();
        data.put("category", category);
        data.put("searchword", searchWord);
        data.put("usergender", userGender);
        return dao.search(data);
    }

    @Override
    public List<RoommateDTO> getFilteredResult(RoommateDTO data, String userGender) {
        Map<String, Character> conditions = new HashMap<>();
        conditions.put("nocturnal", data.getNocturnal());
        conditions.put("smoking", data.getSmoking());
        conditions.put("pet", data.getPet());
        conditions.put("habit1", 'N');
        conditions.put("habit2", 'N');
        conditions.put("habit3", 'N');
        String habits = data.getSleeping_habits();
        if (habits != null) {
            String[] arr = habits.split(",");
            for (int i = 0; i < arr.length; i++) {
                for (int j = 1; j <= 3; j++) {
                    if (arr[i].equals(String.valueOf(j))) {
                        String key = "habit" + String.valueOf(j);
                        conditions.put(key, 'Y');
                    }
                }
            }
        }
        conditions.put("usergender", userGender.charAt(0));
        return dao.dynamicSearch(conditions);
    }

    @Override
    public RoommateUserDTO getUserInfo(int id) {
        return dao.selectUser(id);
    }
}
