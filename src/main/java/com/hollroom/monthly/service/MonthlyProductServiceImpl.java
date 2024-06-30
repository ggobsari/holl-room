package com.hollroom.monthly.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.community.service.FileUploadService;
import com.hollroom.monthly.dao.MonthlyProductDAO;
import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
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
    private final DivisionRepository divisionRepo;
    private final AttachFileRepository attachFileRepo;
    private final FileUploadService fileUploadService;

    @Override
    public void insertProduct(MonthlyProductDTO dto) {
        MonthlyProductEntity entity = mapper.map(dto, MonthlyProductEntity.class);
        dao.insertProduct(entity);
        try {
            String storeFileName = fileUploadService.uploadFile(dto.getRoomImg());
            AttachFileEntity attachFileEntity = new AttachFileEntity(
                    null, // autoIncreament
                    TabType.MONTHLY_PRODUCT, // tabType
                    dto.getRoomImg().getOriginalFilename(), // originalName
                    storeFileName, // storeName
                    entity.getId() // postId
            );
            attachFileRepo.save(attachFileEntity);
            entity.updateImgUrl(storeFileName);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<MonthlyProductDTO> readProductAll() {
        return dao.readProductAll()
                .stream()
                .map(e-> mapper.map(e,MonthlyProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DivisionDTO readMainDivision(String addr) {
        return mapper.map(getMainDivisionFromAddress(addr),DivisionDTO.class);
    }

    @Override
    public List<DivisionDTO> readSubDivision(String addr) {
        DivisionEntity main = getMainDivisionFromAddress(addr);
        return divisionRepo.findByTopDivisionCode(main.mainDivisionCode)
                .stream()
                .map(e->mapper.map(e,DivisionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyProductDTO> readDivisionProduct(String addr) {
        Long divisionCode = getMainDivisionFromAddress(addr).mainDivisionCode;
        return dao.readDivisionProduct(divisionCode)
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private DivisionEntity getMainDivisionFromAddress(String addr) {
        String[] divisions= addr.split(" ");

        DivisionEntity main= divisionRepo.findByName(divisions[0]).get(0);
        for(int i=1;i<divisions.length;i++)
            main = divisionRepo.findByTopDivisionCodeAndName(main.mainDivisionCode,divisions[i]);

        return main;
    }

    private MonthlyProductDTO convertEntityToDTO(MonthlyProductEntity entity) {
        MonthlyProductDTO dto = mapper.map(entity,MonthlyProductDTO.class);

        return dto;
    }
}
