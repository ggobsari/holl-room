package com.hollroom.admin.repository;

import com.hollroom.admin.entity.DailyVisitor;
import com.hollroom.user.entity.UserEntity;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyVisitorRepository extends JpaRepository<DailyVisitor, Long> {
    List<DailyVisitor> findByVisitDate(LocalDate visitDate);
    List<DailyVisitor> findByVisitDateAndUser(LocalDate visitDate, UserEntity user);
}
