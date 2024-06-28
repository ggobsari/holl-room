package com.hollroom.community.service;

import com.hollroom.community.domain.dto.CommunityFileDTO;
import com.hollroom.community.domain.dto.CommunityPagingDTO;
import com.hollroom.community.domain.dto.CommunityRequestDTO;
import com.hollroom.community.domain.dto.CommunityResponseDTO;

import java.util.List;

public interface CommunityService {
    //커뮤니티
    //게시글 등록
    void insert(CommunityRequestDTO requestDTO, List<CommunityFileDTO> fileDTOList);
    //게시글 list보기
    CommunityPagingDTO communityList(String category, String page);

//    CommunityPagingDTO communitySearch(String category, String search, String page);
    //게시물 자세히보기
    CommunityResponseDTO read(String postId);
    //한 게시물에 포함된 첨부파일 가져오기
    List<CommunityFileDTO> fileList(String postId);
    //조회수 +1증가
    void updateViewCounting(Long postId);

    //게시글 수정
    void update(CommunityRequestDTO requestDTO);


}
