package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminMonDTO;

import java.util.List;

public interface AdminMonDAO {
    //월세매물 목록
    List<AdminMonDTO> selectAllAdminMon();
    //월세매물 글 삭제
    void selectAdminMonById(AdminMonDTO adminMon);
}
