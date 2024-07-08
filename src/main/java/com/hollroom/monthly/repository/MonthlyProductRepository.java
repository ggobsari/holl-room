package com.hollroom.monthly.repository;

import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MonthlyProductRepository extends JpaRepository<MonthlyProductEntity,Long> {
    Page<MonthlyProductEntity> findByDivisionCode(Long divisionCode, Pageable pageable);
}

