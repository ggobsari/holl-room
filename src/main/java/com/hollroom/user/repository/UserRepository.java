package com.hollroom.user.repository;

import com.hollroom.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    Optional<UserEntity> findByUserNickname(String userNickname);
    Optional<UserEntity> findByResetToken(String resetToken);
    long countByUserSignupAtBetween(LocalDate start, LocalDate end);
    long countByBanTrueOrIsDeletedTrueAndUserSignupAtBetween(LocalDate start, LocalDate end);

    long countByUserSignupAtAndIsDeletedFalseAndBanFalse(LocalDate date);
}
