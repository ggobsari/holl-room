package com.hollroom.monthly.service;

import com.hollroom.monthly.dao.MonthlyProductDAO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyProductServiceImpl implements MonthlyProductService {
    private final MonthlyProductDAO dao;

    @Override
    public void insertProduct(MonthlyProductDTO dto) {
        MonthlyProductEntity entity = new MonthlyProductEntity(
                dto.getDeposit(),
                dto.getMonthly(),
                dto.getAddress(),
                dto.getFloor_count(),
                dto.getPyeong_count(),
                dto.getRoom_count(),
                dto.getBay_count(),
                dto.getRoom_option(),
                dto.getSecurity_facility(),
                dto.getExpiration_date()
        );
        dao.insertProduct(entity);
    }
}
