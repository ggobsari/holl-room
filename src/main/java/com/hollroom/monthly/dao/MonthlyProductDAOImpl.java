package com.hollroom.monthly.dao;

import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import com.hollroom.monthly.repository.MonthlyProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        List<MonthlyProductEntity> list = new ArrayList<>();
        try {
            list = productRepo.findAll().subList(0,10);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MonthlyProductEntity> readDivisionProduct(Long divisionCode) {
        return productRepo.findByDivisionCode(divisionCode);
    }
}
