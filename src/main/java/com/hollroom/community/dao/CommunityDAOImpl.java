package com.hollroom.community.dao;

import com.hollroom.community.domain.dto.CommunityPagingEntityDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.community.repository.CommunityRepository;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityDAOImpl implements CommunityDAO{
    private final CommunityRepository communityRepository;
    private final AttachFileRepository attachFileRepository;

    @Override
    public void insert(CommunityEntity entity) {
        communityRepository.save(entity);
    }

    @Override
    public void insertFile(List<AttachFileEntity> fileEntityList) {
        for (AttachFileEntity attachFileEntity : fileEntityList) {
            attachFileRepository.save(attachFileEntity);
        }
    }

    @Override
    public CommunityPagingEntityDTO pagingFindAll(String page) {
        CommunityPagingEntityDTO pagingEntityDTO = new CommunityPagingEntityDTO();
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),3, Sort.Direction.DESC,"postId");
        Page<CommunityEntity> pages = communityRepository.findAll(pageRequest);

        pagingEntityDTO.setCommunityEntities(pages.getContent());
        pagingEntityDTO.setTotalPages(pages.getTotalPages());

        //큰 DTO를 리턴
        return pagingEntityDTO;
    }

    @Override
    public CommunityPagingEntityDTO pagingFindCategory(String category, String page) {
        CommunityPagingEntityDTO pagingEntityDTO = new CommunityPagingEntityDTO();
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),3, Sort.Direction.DESC,"postId");
        Page<CommunityEntity> pages = communityRepository.findByCategory(category, pageRequest);

        pagingEntityDTO.setCommunityEntities(pages.getContent());
        pagingEntityDTO.setTotalPages(pages.getTotalPages());
        return pagingEntityDTO;
    }

    @Override
    public CommunityEntity findById(Long postId) {
        return communityRepository.findByPostId(postId);
    }

    @Override
    public List<AttachFileEntity> getFiles(Long postId) {
        return attachFileRepository.findByPostId(postId);
    }

    @Override
    public void updateViewCounting(Long postId) {
        CommunityEntity entity = communityRepository.findByPostId(postId);
        System.out.println("조회수 수정 전::"+entity.getViewCount());
        entity.setViewCount(entity.getViewCount()+1);
        communityRepository.save(entity);

    }

    @Override
    public void update(CommunityEntity entity) {
        //제목, 카테고리, 본문내용
        CommunityEntity updateEntity = communityRepository.findByPostId(entity.getPostId());
        updateEntity.setTitle(entity.getTitle());
        updateEntity.setCategory(entity.getCategory());
        updateEntity.setContent(entity.getContent());
        communityRepository.save(updateEntity);

    }


}
