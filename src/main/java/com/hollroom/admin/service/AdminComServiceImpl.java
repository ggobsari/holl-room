package com.hollroom.admin.service;

import com.hollroom.admin.dao.AdminComDAO;
import com.hollroom.admin.domain.dto.AdminComDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminComServiceImpl implements AdminComService {

    private final AdminComDAO adminCommunityDAO;

    //커뮤니티 전체 글 목록
    @Override
    public List<AdminComDTO> selectAdminCommunity(){
        return adminCommunityDAO.selectAdminCommunity();
    }

    //커뮤니티 글 전체 개수
    @Override
    public Long selectAdminCommunityCount(){
        return adminCommunityDAO.selectAdminCommunityCount();
    }

    //커뮤니티 자유 글 개수
    @Override
    public Long selectAdminCommunityFreeCount(){
        return adminCommunityDAO.selectAdminCommunityFreeCount();
    }

    //커뮤니티 질문 글 개수
    @Override
    public Long selectAdminCommunityQuestionCount(){
        return adminCommunityDAO.selectAdminCommunityQuestionCount();
    }

    //커뮤니티 꿀팁 글 개수
    @Override
    public Long selectAdminCommunityTipCount(){
        return adminCommunityDAO.selectAdminCommunityTipCount();
    }

    //자취레시피 글 개수
    @Override
    public Long selectAdminCommunityRecipeCount(){
        return adminCommunityDAO.selectAdminCommunityRecipeCount();
    }

    //글 삭제
    @Override
    public void deleteAdminCommunity(AdminComDTO adminCommunityDTO) {
        adminCommunityDAO.deleteAdminCommunity(adminCommunityDTO);
    }
}
