package com.hollroom.community.dao;

public interface HeartDAO {

    // 좋아요 +1
    void heartUp(Long postId,Long heartId);
    // 좋아요 -1
    void heartDown(Long postId,Long heartId);
}
