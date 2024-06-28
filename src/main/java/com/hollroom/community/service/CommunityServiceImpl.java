package com.hollroom.community.service;

import com.hollroom.community.dao.CommunityDAO;
import com.hollroom.community.domain.dto.*;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.repository.AttachFileRepository;
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
                    new CommunityEntity(requestDTO.getTitle(),requestDTO.getContent(),0, requestDTO.getCategory(),userRepository.findById(requestDTO.getId()).get());
            List<AttachFileEntity> fileEntityList = new ArrayList<>();

            communityDAO.insert(entity);
        }else{
            List<AttachFileEntity> fileEntityList = new ArrayList<>();
            for (CommunityFileDTO fileDTO: fileDTOList) {
                AttachFileEntity attachFileEntity = new AttachFileEntity(fileDTO.getTabType(),fileDTO.getFileOriginalName(),fileDTO.getFileStoreName());
                fileEntityList.add(attachFileEntity);
            }
            CommunityEntity entity =
                    new CommunityEntity(requestDTO.getTitle(),requestDTO.getContent(),0, requestDTO.getCategory(),userRepository.findById(requestDTO.getId()).get(),fileEntityList);

            communityDAO.insert(entity);
//            communityDAO.insertFile(fileEntityList);
        }

    }

    @Override
    public CommunityPagingDTO communityList(String category, String page) {
        System.out.println("전체조회서비스");
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

            System.out.println("***************************"+responseDTOlist);

            //더 큰 DTO'2'로 옮기기
            pagingDTO.setCommunityResponseDTOList(responseDTOlist);
            System.out.println("확인================>"+pagingDTO);
            pagingDTO.setTotalPages(pagingEntityDTO.getTotalPages());
            System.out.println("총페이지수::"+ pagingDTO.getTotalPages());
        }
        return pagingDTO;
    }

    @Override
    public CommunityResponseDTO read(String postId) {
        CommunityEntity readEntity = communityDAO.findById(Long.parseLong(postId));


        return CommunityResponseDTO.builder()
                .postId(readEntity.getPostId())
                .title(readEntity.getTitle())
                .id(readEntity.getUser().getId())
                .userNickname(readEntity.getUser().getUserNickname())
                .category(readEntity.getCategory())
                .content(readEntity.getContent())
                .updatedAt(readEntity.getUpdatedAt())
                .build();
    }

    @Override
    public List<CommunityFileDTO> fileList(String postId) {
        List<AttachFileEntity> fileEntityList = communityDAO.getFiles(Long.parseLong(postId));
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
    public void updateViewCounting(Long postId) {
        System.out.println("조회수 변경 서비스");
        communityDAO.updateViewCounting(postId);

    }

    @Override
    public void update(CommunityRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();
        CommunityEntity entity = mapper.map(requestDTO,CommunityEntity.class);
//        System.out.println("업데이트 서비스::"+entity);
        communityDAO.update(entity);
    }


}
