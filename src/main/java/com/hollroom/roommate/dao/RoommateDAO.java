package com.hollroom.roommate.dao;

import com.hollroom.roommate.dto.RoommateDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RoommateDAO {
    int register(RoommateDTO dto);
//    int delete(int roommate_id);
//    List<RoommateDTO> search(String keyword);
//    List<RoommateDTO> search(String tag, String keyword);
    RoommateDTO select(int roommate_id);
    List<RoommateDTO> getBoardList();
}
