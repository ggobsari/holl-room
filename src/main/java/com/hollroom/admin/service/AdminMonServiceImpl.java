package com.hollroom.admin.service;

import com.hollroom.admin.dao.AdminMonDAO;
import com.hollroom.admin.domain.dto.AdminMonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMonServiceImpl implements AdminMonService {

    private final AdminMonDAO adminMonDAO;

    @Override
    public List<AdminMonDTO> selectAllAdminMon() {
        return adminMonDAO.selectAllAdminMon();
    }

    @Override
    public void selectAdminMonById(AdminMonDTO adminMon) {
        adminMonDAO.selectAdminMonById(adminMon);
    }
}
