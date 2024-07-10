package com.hollroom.monthly.dao;


import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MonthlyProductDAO {
    void insertProduct(MonthlyProductEntity entity);
    Page<MonthlyProductEntity> readProductAll(Pageable pageable);
    Page<MonthlyProductEntity> readDivisionProduct(Long divisionCode , Pageable pageable);
    Page<MonthlyProductEntity> readDivisionProducts(List<Long> divisionCodes , Pageable pageable);
    MonthlyProductEntity readProduct(Long id);
}
