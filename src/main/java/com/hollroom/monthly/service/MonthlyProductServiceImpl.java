package com.hollroom.monthly.service;

import com.hollroom.monthly.dao.MonthlyProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyProductServiceImpl implements MonthlyProductService {
    private final MonthlyProductDAO dao;
}
