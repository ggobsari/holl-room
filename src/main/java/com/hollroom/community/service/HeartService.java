package com.hollroom.community.service;

public interface HeartService {
    //좋아요
    void upHeartCount(Long postId,Long heartId);
    //좋아요 취소
    void downHeartCount(Long postId,Long heartId);
}
