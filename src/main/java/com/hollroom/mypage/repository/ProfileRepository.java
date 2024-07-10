package com.hollroom.mypage.repository;

import com.hollroom.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserPhoneNumber(String userPhoneNumber);
}
