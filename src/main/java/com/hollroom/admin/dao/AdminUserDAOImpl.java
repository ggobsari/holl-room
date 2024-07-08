package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminUserDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminUserDAOImpl implements AdminUserDAO{
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    //유저 전체 목록
    @Override
    public List<AdminUserDTO> selectAllUsers() {
        return sessionTemplate.selectList("com.hollroom.admin.userList");
    }

    //전체 유저 수
    @Override
    public Long selectAllUsersCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.userCount");
    }

    //카카오 유저 수
    @Override
    public Long selectKakaoUsersCount() {
       return sessionTemplate.selectOne("com.hollroom.admin.userKaKaoCount");
    }

    //네이버 회원 수
    @Override
    public Long selectNaverUsersCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.userNaverCount");
    }

    //구글 회원 수
    @Override
    public Long selectGoogleUsersCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.userGoogleCount");
    }

    //Id로 유저 찾기
    @Override
    public AdminUserDTO selectUserById(Long id) {
        return sessionTemplate.selectOne("com.hollroom.admin.userFindById", id);
    }

    //유저 밴
    @Override
    public void selectUserBan(AdminUserDTO user) {
        sessionTemplate.selectOne("com.hollroom.admin.userBan", user);
    }
}
