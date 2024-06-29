package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;

import java.util.List;

public interface MonthlyProductService {
    void insertProduct(MonthlyProductDTO req);
    List<MonthlyProductDTO> readProductAll();
    DivisionDTO readMainDivision(String addr);
    List<DivisionDTO> readSubDivision(String addr);
    List<MonthlyProductDTO> readDivisionProduct(String addr);
}
