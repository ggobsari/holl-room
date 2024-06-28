package com.hollroom.roommate.service;

import com.hollroom.roommate.dao.RoommateDAO;
import com.hollroom.roommate.dto.RoommateDTO;
import jakarta.transaction.Transactional;
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

//    @Override
//    public int delete(int roommate_id) {
//        return 0;
//    }
//
//    @Override
//    public List<RoommateDTO> search(String keyword) {
//        return List.of();
//    }
//
//    @Override
//    public List<RoommateDTO> search(String tag, String keyword) {
//        return List.of();
//    }

    @Override
    public RoommateDTO getBoard(int roommate_id) {
        return dao.select(roommate_id);
    }

    @Override
    public List<RoommateDTO> getBoardList() {
        return dao.getBoardList();
    }
}
