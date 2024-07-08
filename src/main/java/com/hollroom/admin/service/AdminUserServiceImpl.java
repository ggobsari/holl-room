package com.hollroom.admin.service;

import com.hollroom.admin.dao.AdminUserDAO;
import com.hollroom.admin.domain.dto.AdminUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDAO adminUserDAO;

    //유저 전체 목록
    @Override
    public List<AdminUserDTO> selectAllUsers() {
        return adminUserDAO.selectAllUsers();
    }

    //전체 유저 수
    @Override
    public Long selectAllUsersCount() {
        return adminUserDAO.selectAllUsersCount();
    }

    //카카오 유저 수
    @Override
    public Long selectKakaoUsersCount() {
        return adminUserDAO.selectKakaoUsersCount();
    }

    //네이버 유저 수
    @Override
    public Long selectNaverUsersCount() {
        return adminUserDAO.selectNaverUsersCount();
    }

    //구글 유저 수
    @Override
    public Long selectGoogleUsersCount() {
        return adminUserDAO.selectGoogleUsersCount();
    }

    //Id로 유저 찾기
    @Override
    public AdminUserDTO selectUserById(Long id) {
        return adminUserDAO.selectUserById(id);
    }

    //유저 밴
    @Override
    public void selectUserBan(AdminUserDTO user) {
        adminUserDAO.selectUserBan(user);
    }
}
