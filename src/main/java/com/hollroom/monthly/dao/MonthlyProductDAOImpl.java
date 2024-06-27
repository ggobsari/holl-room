package com.hollroom.monthly.dao;

import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import com.hollroom.monthly.repository.MonthlyProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MonthlyProductDAOImpl implements MonthlyProductDAO {
    private final MonthlyProductRepository productRepo;

    @Override
    public void insertProduct(MonthlyProductEntity entity) {
        productRepo.save(entity);
    }

    @Override
    public List<MonthlyProductEntity> readProductAll() {
        return productRepo.findAll();
    }

    @Override
    public List<MonthlyProductEntity> readDivisionProduct(int divisionCode) {
        return List.of();
    }
}
