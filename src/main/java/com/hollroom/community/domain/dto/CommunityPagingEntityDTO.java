package com.hollroom.community.domain.dto;

import com.hollroom.community.domain.entity.CommunityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityPagingEntityDTO {
    private List<CommunityEntity> communityEntities;
    private int totalPages;

}
