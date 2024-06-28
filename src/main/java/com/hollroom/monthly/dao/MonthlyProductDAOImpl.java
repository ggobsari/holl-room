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
        List<MonthlyProductEntity> list = productRepo.findAll().subList(0,10);
        for(MonthlyProductEntity entity : list){
            System.out.println(entity);
        }
        return list;
    }

    @Override
    public List<MonthlyProductEntity> readDivisionProduct(Long divisionCode) {
        return productRepo.findByDivisionCode(divisionCode);
    }
}
