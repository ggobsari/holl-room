package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductRequestDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductResponseDTO;

import java.util.List;

public interface MonthlyProductService {
    void insertProduct(MonthlyProductRequestDTO req);
    List<MonthlyProductResponseDTO> readProductAll();
    DivisionDTO readMainDivision(String addr);
    List<DivisionDTO> readSubDivision(String addr);
    List<MonthlyProductResponseDTO> readDivisionProduct(String addr);
}
