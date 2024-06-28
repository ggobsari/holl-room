package com.hollroom.user.repository;

import com.hollroom.user.entity.KakaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoRepository extends JpaRepository<KakaoEntity, Long> {
    KakaoEntity findByKakaoUserEmail(String kakaoUserEmail);
}
