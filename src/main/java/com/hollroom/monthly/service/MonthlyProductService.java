package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.MonthlyProductDTO;

import java.util.List;

public interface MonthlyProductService {
    void insertProduct(MonthlyProductDTO req);
    List<MonthlyProductDTO> readProductAll();
    List<MonthlyProductDTO> readDivisionProduct(int divisionCode);
}
