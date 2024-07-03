package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;

import java.util.List;

public interface DivisionService {
    DivisionDTO readMainDivision(String addr);
    List<DivisionDTO> readSubDivisions(String addr);
}
