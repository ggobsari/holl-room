package com.hollroom.monthly.repository;

import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyProductRepository extends JpaRepository<MonthlyProductEntity,Long> {
    List<MonthlyProductEntity> findByDivisionCode(Long divisionCode);
}

