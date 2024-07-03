package com.hollroom.community.dao;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.CommunityPagingEntityDTO;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.domain.entity.CommentsEntity;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.community.repository.CommentsRepository;
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
    private final CommentsRepository commentsRepository;

    @Override
    public Long insert(CommunityEntity entity) {
       return communityRepository.save(entity).getPostId();
    }

    @Override
    public void insertFile(AttachFileEntity entity) {
        attachFileRepository.save(entity);
    }

//    @Override
//    public void insertFile(List<AttachFileEntity> fileEntityList) {
//        for (AttachFileEntity attachFileEntity : fileEntityList) {
//            attachFileRepository.save(attachFileEntity);
//        }
//    }

    //삭제 처리된 게시글은 가져오지 않게
    @Override
    public CommunityPagingEntityDTO pagingFindAll(String page) {
        CommunityPagingEntityDTO pagingEntityDTO = new CommunityPagingEntityDTO();
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),10, Sort.Direction.DESC,"postId");
        //삭제된 게시글은 불러오지 않도록 메소드 변경
        Page<CommunityEntity> pages = communityRepository.findByDeleted(null,pageRequest);

        pagingEntityDTO.setCommunityEntities(pages.getContent());
        pagingEntityDTO.setTotalPages(pages.getTotalPages());
        pagingEntityDTO.setNowPageNumber(pages.getNumber());
        pagingEntityDTO.setPageSize(pages.getSize());
        pagingEntityDTO.setHasNextPage(pages.hasNext());
        pagingEntityDTO.setHasPreviousPage(pages.hasPrevious());
        pagingEntityDTO.setFirstPage(pages.isFirst());
        pagingEntityDTO.setLastPage(pages.isLast());

        //큰 DTO를 리턴
        return pagingEntityDTO;
    }

    @Override
    public CommunityPagingEntityDTO pagingFindCategory(String category, String page) {
        CommunityPagingEntityDTO pagingEntityDTO = new CommunityPagingEntityDTO();
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),10, Sort.Direction.DESC,"postId");
        //삭제된 게시글은 불러오지 않도록 메소드 변경
        Page<CommunityEntity> pages = communityRepository.findByCategoryAndDeleted(category,null,pageRequest);

        pagingEntityDTO.setCommunityEntities(pages.getContent());
        pagingEntityDTO.setTotalPages(pages.getTotalPages());
        pagingEntityDTO.setNowPageNumber(pages.getNumber());
        pagingEntityDTO.setPageSize(pages.getSize());
        pagingEntityDTO.setHasNextPage(pages.hasNext());
        pagingEntityDTO.setHasPreviousPage(pages.hasPrevious());
        pagingEntityDTO.setFirstPage(pages.isFirst());
        pagingEntityDTO.setLastPage(pages.isLast());
        return pagingEntityDTO;
    }

    @Override
    public CommunityEntity findById(Long postId) {
        return communityRepository.findByPostId(postId);
    }

    @Override
    public List<AttachFileEntity> getFiles(Long postId, TabType tab) {
        System.out.println("read 탭타입 수정++++++++++++++++++++++++++++++++++++++++++++++++");
        return attachFileRepository.findByPostIdAndTabType(postId,tab);
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

    @Override
    public void delete(Long postId) {
        //해당 postId로 해당 엔티티 정보를 조회해서 해당 엔티티 정보를 업데이트 삭제여부 및 삭제날짜 활성화
        CommunityEntity deleteEntity = communityRepository.findByPostId(postId);
        deleteEntity.setDeleted("true"); //true라는 스트링값이 있으면 '삭제'라는 의미
        deleteEntity.setDeletedAt(deleteEntity.getUpdatedAt());

        communityRepository.save(deleteEntity);
    }

    @Override
    public void commentInsert(CommentsEntity commentsEntity) {
        commentsRepository.save(commentsEntity);
    }

    @Override
    public List<CommentsEntity> getComments(Long postId) {
        //삭제된 댓글은 가져오지 않도록 수정
//        List<CommentsEntity> commentsEntityList = commentsRepository.findByPostId(postId);
        List<CommentsEntity> commentsEntityList = commentsRepository.findByPostIdAndDeletedCom(postId,null);
        return commentsEntityList;
    }

    @Override
    public void commentUpdate(CommentsEntity commentsEntity) {
        CommentsEntity updateEntity = commentsRepository.findByCommentId(commentsEntity.getCommentId());
        updateEntity.setComments(commentsEntity.getComments());
        commentsRepository.save(updateEntity);
    }

    @Override
    public void commentDelete(Long commentId) {
        CommentsEntity deleteEntity = commentsRepository.findByCommentId(commentId);
        deleteEntity.setDeletedCom("true"); //true라는 스트링값이 있으면 삭제처리되었다는 의미
        deleteEntity.setDeleteAt(deleteEntity.getDeleteAt());

        commentsRepository.save(deleteEntity);
    }

    @Override
    public List<CommunityEntity> searchSimple(String content) {
        return communityRepository.findByContentContaining(content);
    }

    @Override
    public List<CommunityEntity> search(CommunityEntity entity) {
        return communityRepository.findByCategoryAndContentContaining(entity.getCategory(),entity.getContent());
    }


}
