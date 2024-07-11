package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.AdminRoomDTO;

import java.time.LocalDate;
import java.util.List;

public interface AdminRoomService {
    //룸메이트 목록
    List<AdminRoomDTO> selectAllAdminRoommates();
    //룸메이트 글 삭제
    void selectAdminRoommateById(AdminRoomDTO adminRoom);

    long countByCreateAt(LocalDate date);
}
