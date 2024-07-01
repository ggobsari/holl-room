package com.hollroom.mypage.service;


import com.hollroom.mypage.dao.InquiryDAO;
import com.hollroom.mypage.domain.dto.InquiryAttatchDTO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryAttatchEntity;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.repository.InquiryAttachRepository;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryAttachRepository inquiryAttachRepository;
    private final InquiryDAO inquiryDAO;
    private final UserRepository userRepository;

    //문의글 목록
    @Override
    public Page<InquiryDTO> getInquiries(Pageable pageable) {
        return inquiryDAO.findAll(pageable).map(inquiryEntity -> {
            InquiryDTO inquiryDTO = new InquiryDTO();
            inquiryDTO.setPostId(inquiryEntity.getPostId());
            inquiryDTO.setTitle(inquiryEntity.getTitle());
            inquiryDTO.setCategory(inquiryEntity.getCategory());
            inquiryDTO.setCreatedAt(inquiryEntity.getCreatedAt());
            inquiryDTO.setAnswered(inquiryEntity.getAnswerContnet() != null);
            inquiryDTO.setAnsweredAt(inquiryEntity.getAnswerAt());
            return inquiryDTO;
        });
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
    public void saveInquiryFile(List<MultipartFile> files, Long postId) throws Exception {
        for (MultipartFile file : files) { //파일이 없을때까지
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get("src/main/resources/static/mypage/img/profile/", fileName).toAbsolutePath().normalize();

            // 중복 파일 체크 및 파일명 변경
            int count = 1;
            while (Files.exists(filePath)) {
                String fileBaseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                String dupfileName = fileBaseName + "(" + count + ")" + fileExtension;
                filePath = Paths.get("src/main/resources/static/mypage/img/profile/", dupfileName).toAbsolutePath().normalize();
                count++;
            }

            Files.copy(file.getInputStream(), filePath);

            InquiryAttatchEntity inquiryAttatchEntity = new InquiryAttatchEntity();
            inquiryAttatchEntity.setFileOriginalName(fileName);
            inquiryAttatchEntity.setFileStoreName(filePath.toString());
            inquiryAttatchEntity.setPostId(postId);

            inquiryDAO.saveAttachFile(inquiryAttatchEntity); //파일저장
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
        inquiryDTO.setAnsweredAt(inquiryEntity.getAnswerAt());
        inquiryDTO.setUserId(inquiryEntity.getUser().getId());
        return inquiryDTO;
    }

    //첨부파일 불러오기
    @Override
    public List<InquiryAttatchDTO> getAttachmentsByPostId(Long postId) {
        List<InquiryAttatchEntity> entities = inquiryDAO.findAttachmentsByPostId(postId);
        return entities.stream().map(entity -> {
            InquiryAttatchDTO dto = new InquiryAttatchDTO();
            dto.setFileName(entity.getFileOriginalName());
            dto.setFilePath(entity.getFileStoreName());
            return dto;
        }).collect(Collectors.toList());
    }
}

