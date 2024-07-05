package com.hollroom.mypage.service;


import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.CommunityFileDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.service.FileUploadService;
import com.hollroom.mypage.dao.InquiryDAO;
import com.hollroom.mypage.domain.dto.InquiryAttatchDTO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private final InquiryDAO inquiryDAO;
    private final FileUploadService fileUploadService;


    //문의글 목록
    @Override
    public Map<String, Object> getInquiriesByUserId(Long userId, int page) {
        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC,"updatedAt"));
        Map<String, Object> result = new HashMap<>();
        var postPage = inquiryDAO.findByUserId(userId, pageRequest);
        result.put("posts", postPage.getContent());
        result.put("totalPages", postPage.getTotalPages());
        result.put("totalPosts", postPage.getTotalElements());
        return result;
    }

    //문의글 업로드
    @Override
    public InquiryEntity saveInquiry(InquiryDTO inquiryDTO) throws Exception {
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setCategory(inquiryDTO.getCategory());
        inquiryEntity.setTitle(inquiryDTO.getTitle());
        inquiryEntity.setContent(inquiryDTO.getContent());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(inquiryDTO.getUserId());
        inquiryEntity.setUser(userEntity);

        inquiryDAO.saveInquiry(inquiryEntity); //문의글 저장
        return inquiryEntity;
    }

    //첨부파일 업로드
    @Override
    public void saveInquiryFile(List<InquiryAttatchDTO> files, Long postId) throws IOException {
        for(InquiryAttatchDTO inquiryAttatchDTO : files){
            AttachFileEntity attachFileEntity = new AttachFileEntity();
            attachFileEntity.setPostId(postId);
            attachFileEntity.setTabType(inquiryAttatchDTO.getTabType());
            attachFileEntity.setFileStoreName(inquiryAttatchDTO.getFileStoreName());
            attachFileEntity.setFileOriginalName(inquiryAttatchDTO.getFileOriginalName());

            inquiryDAO.saveAttachFile(attachFileEntity);
        }
    }

    //문의글 상세보기
    @Override
    public InquiryDTO getInquiryById(Long id) {
        InquiryEntity inquiryEntity = inquiryDAO.findById(id);
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setPostId(inquiryEntity.getPostId());
        inquiryDTO.setTitle(inquiryEntity.getTitle());
        inquiryDTO.setCategory(inquiryEntity.getCategory());
        inquiryDTO.setContent(inquiryEntity.getContent());
        inquiryDTO.setCreatedAt(inquiryEntity.getCreatedAt());
        inquiryDTO.setAnswered(inquiryEntity.getAnswerContnet() != null);
        inquiryDTO.setAnswerContent(inquiryEntity.getAnswerContnet());
        inquiryDTO.setAnsweredAt(inquiryEntity.getAnswerAt());
        inquiryDTO.setUserId(inquiryEntity.getUser().getId());
        return inquiryDTO;
    }

    //첨부파일 불러오기
    @Override
    public List<InquiryAttatchDTO> getAttachmentsByPostId(Long postId, TabType tabType) {
        List<AttachFileEntity> entities = inquiryDAO.findAttachmentsByPostId(postId, tabType);
        return entities.stream().map(entity -> {
            InquiryAttatchDTO dto = new InquiryAttatchDTO();
            dto.setFileOriginalName(entity.getFileOriginalName());
            dto.setFileStoreName(entity.getFileStoreName());
            return dto;
        }).collect(Collectors.toList());
    }
}

