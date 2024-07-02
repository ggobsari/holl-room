package com.hollroom.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityPagingDTO {
    private List<CommunityResponseDTO> communityResponseDTOList;
    private int totalPages; //전체 페이지 수
    private int nowPageNumber; //현재 페이지 번호
    private int pageSize; // 한 페이지에 출력되는 데이터
    private boolean hasNextPage; //다음 페이지 존재 여부
    private boolean hasPreviousPage; // 이전 페이지 존재 여부
    private boolean firstPage; //첫번째 페이지 인지
    private boolean lastPage;  //마지막 페이지 인지


}
