package com.hollroom.community.repository;

import com.hollroom.community.domain.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity,Long> {
    List<CommentsEntity> findByPostId(Long postId);
    CommentsEntity findByCommentId(Long commentId);
    List<CommentsEntity> findByPostIdAndDeletedCom(Long postId, String deleted);
}
