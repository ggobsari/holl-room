package com.hollroom.mypage.dao;

import com.hollroom.admin.domain.dto.AdminComDTO;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MywriteDAOImpl implements MywriteDAO {
     @Autowired
    private SqlSessionTemplate sessionTemplate;


    @Override
    public List<AdminComDTO> myCommunityList(Long id) {
        return sessionTemplate.selectList("com.hollroom.mypage.communityList", id);
    }

    @Override
    public List<AdminComDTO> myCommunityListCategory(AdminComDTO adminComDTO) {
        return sessionTemplate.selectList("com.hollroom.mypage.communityListCategory", adminComDTO);
    }

    //Roommate userId 글 목록
    @Override
    public List<MyRoommateDTO> selectMyRoommateById(Long id) {
        return sessionTemplate.selectList("com.hollroom.roommate.selectByUserId", id);
    }
}
