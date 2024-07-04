package com.hollroom.monthly.dao;


import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface MonthlyProductDAO {
    void insertProduct(MonthlyProductEntity entity);
    Page<MonthlyProductEntity> readProductAll(Pageable pageable);
    Page<MonthlyProductEntity> readDivisionProduct(Long divisionCode , Pageable pageable);
}
