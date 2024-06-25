package com.hollroom.monthly.service;

import com.hollroom.monthly.dao.MonthlyProductDAO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MonthlyProductServiceImpl implements MonthlyProductService {
    private final MonthlyProductDAO dao;
    private final ModelMapper mapper;

    @Override
    public void insertProduct(MonthlyProductDTO dto) {
        MonthlyProductEntity entity = mapper.map(dto, MonthlyProductEntity.class);
        dao.insertProduct(entity);
    }

    @Override
    public List<MonthlyProductDTO> readProductAll() {
        return dao.readProductAll()
                .stream()
                .map(e-> mapper.map(e,MonthlyProductDTO.class))
                .collect(Collectors.toList());
    }
}
