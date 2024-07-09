package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;

import java.util.List;

public interface DivisionService {
    DivisionDTO readMainDivision(String addr);
    String getAddress(Long divisionCode);
    List<DivisionDTO> readSubDivisions(String addr);
    List<Long> readSubDivisionCodes(Long divisionCode);
}
