package com.hollroom.monthly.repository;

import com.hollroom.monthly.domain.entity.MonthlyTrendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyTrendRepository extends JpaRepository<MonthlyTrendEntity,Long> {
    MonthlyTrendEntity findByDateCodeAndDivisionCode(int dateCode, Long divisionCode);
}
