package com.hollroom.community.service;

import com.hollroom.common.TabType;
import com.hollroom.community.dao.CommunityDAO;
import com.hollroom.community.domain.dto.*;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommentsEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.domain.entity.HeartEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityDAO communityDAO;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void insert(CommunityRequestDTO requestDTO, List<CommunityFileDTO> fileDTOList) {

        if (fileDTOList.size()==0){
            CommunityEntity entity =
                    new CommunityEntity(requestDTO.getTitle(),requestDTO.getContent(),0, requestDTO.getCategory(),userRepository.findById(requestDTO.getId()).get(),0);

            Long postId = communityDAO.insert(entity);
//            System.out.println("첨부파일 없을 경우의 postId::"+ postId);
        }else{
            CommunityEntity entity =
                    new CommunityEntity(requestDTO.getTitle(),requestDTO.getContent(),0, requestDTO.getCategory(),userRepository.findById(requestDTO.getId()).get(),0);

            Long postId = communityDAO.insert(entity);
//            System.out.println("첨부파일 있을 경우의 postId::"+ postId);
            List<AttachFileEntity> fileEntityList = new ArrayList<>();
            for (CommunityFileDTO fileDTO: fileDTOList) {
                AttachFileEntity attachFileEntity = new AttachFileEntity(postId,fileDTO.getTabType(),fileDTO.getFileOriginalName(),fileDTO.getFileStoreName());
                fileEntityList.add(attachFileEntity);
            }
            for(AttachFileEntity fileEntity:fileEntityList){
                communityDAO.insertFile(fileEntity);
            }
            //현재 문제가 되는 지점
//            CommunityEntity entity =
//                    new CommunityEntity(requestDTO.getTitle(),requestDTO.getContent(),0, requestDTO.getCategory(),userRepository.findById(requestDTO.getId()).get(),fileEntityList);
//
//            communityDAO.insert(entity);
//            communityDAO.insertFile(fileEntityList);
        }

    }

    @Override
    @Transactional
    public CommunityPagingDTO communityList(String category, String page) {
//        System.out.println("전체조회서비스");
        CommunityPagingDTO pagingDTO = new CommunityPagingDTO();
        CommunityPagingEntityDTO pagingEntityDTO = null;
        List<CommunityResponseDTO> responseDTOlist = null;


        if (category!=null){
            if (category.equals("all")){ //전체조회
                pagingEntityDTO = communityDAO.pagingFindAll(page);

            }else{ //카테고리별 조회
                pagingEntityDTO = communityDAO.pagingFindCategory(category, page);

            }
            List<CommunityEntity> entityList = pagingEntityDTO.getCommunityEntities();
            ModelMapper mapper = new ModelMapper();
            responseDTOlist = entityList.stream()
                    .map(entity -> mapper.map(entity,CommunityResponseDTO.class))
                    .collect(Collectors.toList());

//            System.out.println("***************************"+responseDTOlist);

            //더 큰 DTO'2'로 옮기기
            pagingDTO.setCommunityResponseDTOList(responseDTOlist);
            pagingDTO.setTotalPages(pagingEntityDTO.getTotalPages());
            pagingDTO.setNowPageNumber(pagingEntityDTO.getNowPageNumber());
            pagingDTO.setPageSize(pagingEntityDTO.getPageSize());
            pagingDTO.setHasNextPage(pagingEntityDTO.isHasNextPage());
            pagingDTO.setHasPreviousPage(pagingEntityDTO.isHasPreviousPage());
            pagingDTO.setFirstPage(pagingEntityDTO.isFirstPage());
            pagingDTO.setLastPage(pagingEntityDTO.isLastPage());
        }
        return pagingDTO;
    }

    @Override
    public CommunityPagingDTO deepSearch(String category, String fieldOption, String content, String page) {
//        System.out.println("복합 검색 서비스");
        CommunityPagingDTO pagingDTO = new CommunityPagingDTO();
        CommunityPagingEntityDTO pagingEntityDTO = null;
        List<CommunityResponseDTO> responseDTOlist = null;
        //카테고리, 필드(containing)
        if (category!=null){
            if (category.equals("all")){ //필드만 조건 검색 content, page 매개변수

                if(fieldOption.equals("title")){
                    //필드:제목 검색
                    pagingEntityDTO = communityDAO.deepSearchTitle(content,page);
                }else if (fieldOption.equals("content")){
                    //필드: 본문내용 검색
                    pagingEntityDTO = communityDAO.deepSearchContent(content,page);
                }else if (fieldOption.equals("writer")){
                    //필드: 작성자 검색
                    pagingEntityDTO = communityDAO.deepSearchWriter(content,page);
                }else if(fieldOption.equals("both")){
                    //필드: 제목+내용 검색
                    pagingEntityDTO = communityDAO.deepSearchTitleAndContent(content,page);
                }

            }else{ //카테고리 필드 둘다 조건검색 포함 category, content, page 매개변수

                if(fieldOption.equals("title")){
                    //필드:제목 검색
                    pagingEntityDTO = communityDAO.deepSearchCateTitle(category,content,page);
                }else if (fieldOption.equals("content")){
                    //필드: 본문내용 검색
                    pagingEntityDTO = communityDAO.deepSearchCateContent(category,content,page);
                }else if (fieldOption.equals("writer")){
                    //필드: 작성자 검색
                    pagingEntityDTO = communityDAO.deepSearchCateWriter(category,content,page);
                }else if(fieldOption.equals("both")){
                    //필드: 제목+내용 검색
                    pagingEntityDTO = communityDAO.deepSearchCateTitleAndContent(category, content, page);
                }

            }

            if(pagingEntityDTO!=null) {
                List<CommunityEntity> entityList = pagingEntityDTO.getCommunityEntities();
                ModelMapper mapper = new ModelMapper();
                responseDTOlist = entityList.stream()
                        .map(entity -> mapper.map(entity, CommunityResponseDTO.class))
                        .collect(Collectors.toList());

//                System.out.println("***************************" + responseDTOlist);

                //더 큰 DTO'2'로 옮기기
                pagingDTO.setCommunityResponseDTOList(responseDTOlist);
                pagingDTO.setTotalPages(pagingEntityDTO.getTotalPages());
                pagingDTO.setNowPageNumber(pagingEntityDTO.getNowPageNumber());
                pagingDTO.setPageSize(pagingEntityDTO.getPageSize());
                pagingDTO.setHasNextPage(pagingEntityDTO.isHasNextPage());
                pagingDTO.setHasPreviousPage(pagingEntityDTO.isHasPreviousPage());
                pagingDTO.setFirstPage(pagingEntityDTO.isFirstPage());
                pagingDTO.setLastPage(pagingEntityDTO.isLastPage());
            }
        }
        return pagingDTO;
    }
    //상위 조회수
    @Override
    public List<CommunityResponseDTO> findByTopViewCount() {
        List<CommunityResponseDTO> topList = null;
        List<CommunityEntity> entityList = communityDAO.findTopByViewCount();

        ModelMapper mapper = new ModelMapper();
        topList = entityList.stream().map(entity -> mapper.map(entity,CommunityResponseDTO.class)).collect(Collectors.toList());

        return topList;
    }


    @Override
    @Transactional
    public CommunityResponseDTO read(String postId) {
        CommunityEntity readEntity = communityDAO.findById(Long.parseLong(postId));


        return CommunityResponseDTO.builder()
                .postId(readEntity.getPostId())
                .title(readEntity.getTitle())
                .id(readEntity.getUser().getId())
                .userNickname(readEntity.getUser().getUserNickname())
                .category(readEntity.getCategory())
                .content(readEntity.getContent())
                .createdAt(readEntity.getCreatedAt())
                .heartCount(readEntity.getHeartCount())
                .build();
    }

    @Override
    public List<CommunityFileDTO> fileList(String postId, TabType tab) {
        List<AttachFileEntity> fileEntityList = communityDAO.getFiles(Long.parseLong(postId),tab);
        List<CommunityFileDTO> fileDTOList = new ArrayList<>();
        for (AttachFileEntity entity: fileEntityList){
            fileDTOList.add(CommunityFileDTO.builder().fileId(entity.getFileId())
                                                    .tabType(entity.getTabType())
                                                    .fileStoreName(entity.getFileStoreName())
                                                    .fileOriginalName(entity.getFileOriginalName()).build());
        }
        return fileDTOList;
    }

    @Override
    @Transactional
    public void updateViewCounting(Long postId) {
//        System.out.println("조회수 변경 서비스");
        communityDAO.updateViewCounting(postId);

    }

    @Override
    @Transactional
    public void update(CommunityRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();
        CommunityEntity entity = mapper.map(requestDTO,CommunityEntity.class);
//        System.out.println("업데이트 서비스::"+entity);
        communityDAO.update(entity);
    }

    @Override
    @Transactional
    public void delete(String postId) {
        communityDAO.delete(Long.parseLong(postId));
    }

    @Override
    public void commentInsert(CommentsRequestDTO commentsRequestDTO) {
        CommentsEntity entity = new CommentsEntity(commentsRequestDTO.getComments(),commentsRequestDTO.getPostId()
                                ,commentsRequestDTO.getUserId());

        communityDAO.commentInsert(entity);

    }

    @Override
    public List<CommentsResponseDTO> commentsList(String postId) {
        List<CommentsResponseDTO> commentsResponseDTOList = null;
        List<CommentsEntity> commentsEntityList = communityDAO.getComments(Long.parseLong(postId));

        ModelMapper mapper = new ModelMapper();
        commentsResponseDTOList = commentsEntityList.stream()
                .map(entity -> mapper.map(entity,CommentsResponseDTO.class))
                .collect(Collectors.toList());

        //유저아이디에 해당하는 유저 닉네임을 추가적으로 찾아서 DTO에 저장
        for (CommentsResponseDTO commentsResponseDTO : commentsResponseDTOList){
            UserEntity entity = userRepository.findById(commentsResponseDTO.getUserId()).get();
            commentsResponseDTO.setNickName(entity.getUserNickname());
        }

        return commentsResponseDTOList;
    }

    @Override
    public void commentUpdate(CommentsRequestDTO commentsRequestDTO) {
        ModelMapper mapper = new ModelMapper();
        CommentsEntity entity = mapper.map(commentsRequestDTO,CommentsEntity.class);
//        System.out.println("DTO=>entity전환 comments_update+++++++++++++++++");
        communityDAO.commentUpdate(entity);
    }

    @Override
    public void commentDelete(String commentId) {
        communityDAO.commentDelete(Long.parseLong(commentId));
    }

    @Override
    public List<CommunityResponseDTO> search(String category, String search) {
        List<CommunityResponseDTO> communityResponseDTOList = null;
        List<CommunityEntity> searchedEntity = null;
        if (category.equals("all")){
            searchedEntity = communityDAO.searchSimple(search);
        }else{
            CommunityEntity entity = new CommunityEntity(category,search);
            searchedEntity = communityDAO.search(entity);
        }
        ModelMapper mapper = new ModelMapper();
        communityResponseDTOList = searchedEntity.stream()
                .map(searchEntity -> mapper.map(searchEntity,CommunityResponseDTO.class))
                .collect(Collectors.toList());

        return communityResponseDTOList;

    }

    @Override
    public HeartDTO getHeart(Long postId, Long userId) {
        boolean heartIs = communityDAO.countHeart(postId,userId);
        HeartEntity heartEntity = null;
        if (heartIs == false){
            //생성
            communityDAO.createHeart(postId,userId,TabType.COMMUNITY,"0");
            //가져오기
            heartEntity = communityDAO.getHeart(postId,userId,TabType.COMMUNITY);
        }else if (heartIs == true){
            //가져오기
            heartEntity = communityDAO.getHeart(postId,userId,TabType.COMMUNITY);
        }

        return HeartDTO.builder()
                .heartId(heartEntity.getHeartId())
                .postId(heartEntity.getPostId())
                .userId(heartEntity.getUserId())
                .checkHeart(heartEntity.getCheckHeart())
                .heartId(heartEntity.getHeartId()).build();
    }


}
