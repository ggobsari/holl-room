package com.hollroom.roommate.service;

import com.hollroom.roommate.dao.RoommateDAO;
import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<RoommateDTO> getBoardList() {
        return dao.getBoardList();
    }

    @Override
    public RoommateUserDTO getUserInfo(int id) {
        return dao.selectUser(id);
    }
}
