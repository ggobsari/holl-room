package com.hollroom.monthly.repository;

import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface MonthlyProductRepository extends JpaRepository<MonthlyProductEntity,Long> {
    List<MonthlyProductEntity> findByDivisionCode(Long divisionCode);
    //List<MonthlyProductEntity> findByDivisionCode(Long divisionCode, Pageable pageable);
    List<MonthlyProductEntity> findAll();
    //List<MonthlyProductEntity> findAll(Pageable pageable);
}

