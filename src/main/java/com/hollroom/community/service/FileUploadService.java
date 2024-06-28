package com.hollroom.community.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.CommunityFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${file.dir}")
    private String uploadPath;

    public List<CommunityFileDTO> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<CommunityFileDTO> fileDTOList = new ArrayList<>();
        for (MultipartFile multipartFile:multipartFiles){
            if(!multipartFile.isEmpty()){
                String storeFileName = uploadFile(multipartFile);
                //원본, 저장되는 파일 이름만 dto로 전달
                fileDTOList.add(new CommunityFileDTO(null, TabType.COMMUNITY,multipartFile.getOriginalFilename(),storeFileName));
            }
        }
        return fileDTOList;
    }
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String storeFileName = "";
        if(!multipartFile.isEmpty()){
            String originalFileName = multipartFile.getOriginalFilename();
            storeFileName = createStoreFileName(originalFileName);
            //확인용 매핑
            System.out.println(originalFileName+"::"+storeFileName);
            multipartFile.transferTo(new File(getUploadPath(storeFileName)));
        }
        return storeFileName;
    }
    public String getUploadPath(String fileName){
        return uploadPath+fileName;
    }

    private String createStoreFileName(String originalFileName){
        int position = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(position+1);
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+ext;
    }
}
