package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminComDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminComDAOImpl implements AdminComDAO {
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    //커뮤니티 글 목록
    @Override
    public List<AdminComDTO> selectAdminCommunity() {
        return sessionTemplate.selectList("com.hollroom.admin.communityList");
    }

    //커뮤니티 글 전체 개수
    @Override
    public Long selectAdminCommunityCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.communityCount");
    }

    //커뮤니티 자유 글 개수
    @Override
    public Long selectAdminCommunityFreeCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.communityFreeCount");
    }

    //커뮤니티 질문 글 개수
    @Override
    public Long selectAdminCommunityQuestionCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.communityQuestionCount");

    }
    //커뮤니티 꿀팁 글 개수
    @Override
    public Long selectAdminCommunityTipCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.communityTipCount");

    }
    //자취레시피 글 개수
    @Override
    public Long selectAdminCommunityRecipeCount() {
        return sessionTemplate.selectOne("com.hollroom.admin.communityRecipeCount");
    }

    @Override
    public void deleteAdminCommunity(AdminComDTO adminCommunityDTO) {
        sessionTemplate.update("com.hollroom.admin.communityDelete", adminCommunityDTO);
    }
}
