package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminComDTO;

import java.util.List;

public interface AdminComDAO {
    //커뮤니티 전체 글 목록
    List<AdminComDTO> selectAdminCommunity();
    //커뮤니티 글 전체 개수
    Long selectAdminCommunityCount();
    //커뮤니티 자유 글 개수
    Long selectAdminCommunityFreeCount();
    //커뮤니티 질문 글 개수
    Long selectAdminCommunityQuestionCount();
    //커뮤니티 꿀팁 글 개수
    Long selectAdminCommunityTipCount();
    //자취레시피 글 개수
    Long selectAdminCommunityRecipeCount();
    //글 삭제
    void deleteAdminCommunity(AdminComDTO adminCommunityDTO);
}