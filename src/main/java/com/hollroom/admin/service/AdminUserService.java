package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.AdminUserDTO;

import java.util.List;

public interface AdminUserService {
    //유저 전체 목록
    List<AdminUserDTO> selectAllUsers();
    //전체 유저 수
    Long selectAllUsersCount();
    //카카오 유저 수
    Long selectKakaoUsersCount();
    //네이버 유저 수
    Long selectNaverUsersCount();
    //구글 유저 수
    Long selectGoogleUsersCount();
    //Id로 유저 찾기
    AdminUserDTO selectUserById(Long id);
    //유저 밴
    void selectUserBan(AdminUserDTO user);
}
