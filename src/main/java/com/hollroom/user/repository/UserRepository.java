package com.hollroom.user.repository;

import com.hollroom.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    Optional<UserEntity> findByUserNickname(String userNickname);
    Optional<UserEntity> findByResetToken(String resetToken);
}
