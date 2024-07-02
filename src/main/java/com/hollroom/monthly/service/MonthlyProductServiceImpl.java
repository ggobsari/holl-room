package com.hollroom.monthly.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.community.service.FileUploadService;
import com.hollroom.monthly.dao.MonthlyProductDAO;
import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductRequestDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductResponseDTO;
import com.hollroom.monthly.domain.entity.DivisionEntity;
import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import com.hollroom.monthly.repository.DivisionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MonthlyProductServiceImpl implements MonthlyProductService {
    private final MonthlyProductDAO dao;
    private final ModelMapper mapper;
    private final AttachFileRepository attachFileRepo;
    private final FileUploadService fileUploadService;
    private final DivisionService divisionService;
    private final MonthlyTrendRepository trendRepo;

    @Override
    public void insertProduct(MonthlyProductRequestDTO dto) {
        // 매물 dto를 entity로 변환
        MonthlyProductEntity entity = mapper.map(dto, MonthlyProductEntity.class);

        // 매물 dao에 entity를 보내서 monthly_product 테이블에 저장
        dao.insertProduct(entity);

        try {
            // post로 받아온 dto의 MultipartFile을, fileUploadService을 이용해 storeFileName 생성
            String storeFileName = fileUploadService.uploadFile(dto.getRoomImg());

            // 생성한 storeFileName을 사용해서 AttachFileEntity 생성
            AttachFileEntity attachFileEntity = new AttachFileEntity(
                    null, // autoIncreament
                    TabType.MONTHLY_PRODUCT, // tabType
                    dto.getRoomImg().getOriginalFilename(), // originalName
                    storeFileName, // storeName
                    entity.getId() // postId(매물자체 Id)
            );

            // AttachFileRepository으로 생성한 Entity를 저장
            attachFileRepo.save(attachFileEntity);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<MonthlyProductResponseDTO> readProductAll() {
        return dao.readProductAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<MonthlyProductResponseDTO> readDivisionProduct(String addr) {
        Long divisionCode = divisionService.readMainDivision(addr).mainDivisionCode;
        return dao.readDivisionProduct(divisionCode)
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private MonthlyProductResponseDTO convertEntityToDTO(MonthlyProductEntity entity) {
        MonthlyProductResponseDTO dto = mapper.map(entity,MonthlyProductResponseDTO.class);
        dto.setRoomImg(attachFileRepo.findByPostIdAndTabType(dto.getId(),TabType.MONTHLY_PRODUCT).get(0).getFileStoreName());
        return dto;
    }

}
