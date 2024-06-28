package com.hollroom.monthly.repository;

import com.hollroom.monthly.domain.entity.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepository extends JpaRepository<DivisionEntity, Integer> {
    DivisionEntity findByTopDivisionCodeAndName(Long topDivisionCode, String name);
    List<DivisionEntity> findByName(String name);
    List<DivisionEntity> findByTopDivisionCode(Long topDivisionCode);
}
