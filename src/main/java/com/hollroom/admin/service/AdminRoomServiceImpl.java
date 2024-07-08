package com.hollroom.admin.service;

import com.hollroom.admin.dao.AdminRoomDAO;
import com.hollroom.admin.domain.dto.AdminRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRoomServiceImpl implements AdminRoomService {
    @Autowired
    private AdminRoomDAO adminRoomDAO;

    //룸메이트 글 목록
    @Override
    public List<AdminRoomDTO> selectAllAdminRoommates() {
        return adminRoomDAO.selectAllAdminRoommates();
    }

    //룸메이트 Id로 글 찾기
    @Override
    public void selectAdminRoommateById(AdminRoomDTO adminRoom) {
        adminRoomDAO.selectAdminRoommateById(adminRoom);
    }
}
