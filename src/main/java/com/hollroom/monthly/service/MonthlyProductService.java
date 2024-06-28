package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;

import java.util.List;

public interface MonthlyProductService {
    void insertProduct(MonthlyProductDTO req);
    List<MonthlyProductDTO> readProductAll();
    DivisionDTO readMainDivision(String topDivision,String mainDivision);
    List<DivisionDTO> readSubDivision(String topDivision);
    List<MonthlyProductDTO> readDivisionProduct(String division);
}
