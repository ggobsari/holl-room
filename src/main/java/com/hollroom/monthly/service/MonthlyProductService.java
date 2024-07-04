package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductRequestDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductResponseDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface MonthlyProductService {
    void insertProduct(MonthlyProductRequestDTO req);
    Page<MonthlyProductResponseDTO> readProductAll(Pageable pageable);
    Page<MonthlyProductResponseDTO> readDivisionProduct(String addr, Pageable pageable);
}
