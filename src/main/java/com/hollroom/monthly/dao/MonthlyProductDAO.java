package com.hollroom.monthly.dao;


import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.domain.entity.MonthlyProductEntity;

import java.util.List;

public interface MonthlyProductDAO {
    void insertProduct(MonthlyProductEntity entity);
    List<MonthlyProductEntity> readProductAll();
    List<MonthlyProductEntity> readDivisionProduct(Long divisionCode);
}
