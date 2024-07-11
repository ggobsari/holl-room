package com.hollroom.admin.repository;

import com.hollroom.admin.domain.entity.DailyVisitor;
import com.hollroom.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyVisitorRepository extends JpaRepository<DailyVisitor, Long> {

    List<DailyVisitor> findByVisitDate(LocalDate visitDate);

    List<DailyVisitor> findByVisitDateAndUser(LocalDate visitDate, UserEntity user);

    long countDailyVisitorByVisitDate(LocalDate date);

//    @Query("SELECT COUNT(dv) FROM DailyVisitor dv WHERE dv.visitDate = :date")
//    long countDailyVisitorByVisitDate(@Param("date") LocalDate date);
}
