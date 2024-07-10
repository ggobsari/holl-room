package com.hollroom.community.domain.entity;

import com.hollroom.common.TabType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "heart")
public class HeartEntity {
    @Id
    @GeneratedValue
    private Long heartId;

    private Long userId;
    private Long postId;

    @Enumerated(EnumType.ORDINAL)
    private TabType tabType;

    @Column(length = 1)
    private String checkHeart;

    //insert
    public HeartEntity(Long postId, Long userId, TabType tabType, String checkHeart) {
        this.postId = postId;
        this.userId = userId;
        this.tabType = tabType;
        this.checkHeart = checkHeart;
    }
}
