package com.hollroom.community.dao;

import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.domain.entity.HeartEntity;
import com.hollroom.community.repository.CommunityRepository;
import com.hollroom.community.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeartDAOImpl implements HeartDAO{
    private final HeartRepository heartRepository;
    private final CommunityRepository communityRepository;


    @Override
    public void heartUp(Long postId,Long heartId) {
        HeartEntity heartEntity = heartRepository.findByHeartId(heartId);
        heartEntity.setCheckHeart("1");
        CommunityEntity communityEntity = communityRepository.findByPostId(postId);
        communityEntity.setHeartCount(communityEntity.getHeartCount()+1);

        heartRepository.save(heartEntity);
        communityRepository.save(communityEntity);
    }

    @Override
    public void heartDown(Long postId,Long heartId) {
        HeartEntity heartEntity = heartRepository.findByHeartId(heartId);
        heartEntity.setCheckHeart("0");
        CommunityEntity communityEntity = communityRepository.findByPostId(postId);
        communityEntity.setHeartCount(communityEntity.getHeartCount()-1);

        heartRepository.save(heartEntity);
        communityRepository.save(communityEntity);
    }
}
