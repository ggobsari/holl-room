package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.MonthlyTrendDTO;

import java.util.Map;

public interface MonthlyTrendService {
    MonthlyTrendDTO readMonthlyTrend(Long divisionCode);
    Map<Long,MonthlyTrendDTO> readMonthlyTrendsByAddress(String address);
}
