package com.hollroom.community.repository;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.domain.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<HeartEntity,Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    HeartEntity findByPostIdAndUserIdAndTabType(Long postId, Long userId, TabType tab);

    HeartEntity findByHeartId(Long heartId);
}
