package com.hollroom.community.service;

import com.hollroom.community.dao.HeartDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{
    private final HeartDAO heartDAO;

    @Override
    public void upHeartCount(Long postId,Long heartId) {
//        System.out.println("좋아요 서비스 (증가)");
        heartDAO.heartUp(postId,heartId);
    }

    @Override
    public void downHeartCount(Long postId,Long heartId) {
//        System.out.println("좋아요 서비스 (감소)");
        heartDAO.heartDown(postId,heartId);
    }
}
