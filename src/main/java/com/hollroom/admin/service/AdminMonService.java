package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.AdminMonDTO;

import java.util.List;

public interface AdminMonService {
    //월세매물 목록
    List<AdminMonDTO> selectAllAdminMon();
    //월세매물 글 삭제
    void selectAdminMonById(AdminMonDTO adminMon);
}
