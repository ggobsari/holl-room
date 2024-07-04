package com.hollroom.community.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.*;

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
    List<CommunityFileDTO> fileList(String postId, TabType tab);
    //조회수 +1증가
    void updateViewCounting(Long postId);

    //게시글 수정
    void update(CommunityRequestDTO requestDTO);

    //해당 게시글 삭제(비활성화)
    void delete(String postId);

    //해당 게시글 댓글 등록
    void commentInsert(CommentsRequestDTO commentsRequestDTO);

    //해당 게시글 댓글 모두 불러오기
    List<CommentsResponseDTO> commentsList(String postId);

    // 해당 댓글 수정
    void commentUpdate(CommentsRequestDTO commentsRequestDTO);

    //해당 댓글 삭제(비활성화)
    void commentDelete(String commentId);

    //게시글 검색 (페이징X)
    List<CommunityResponseDTO> search(String category, String search);


}
