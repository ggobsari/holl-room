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
    private int totalPages;


}
