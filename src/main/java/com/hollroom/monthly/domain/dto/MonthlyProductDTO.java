package com.hollroom.monthly.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyProductDTO {
    private long id;
    private long user_id;
    private int deposit;
    private int monthly;
    private String address;
    private int floor_count;
    private int pyeong_count;
    private int room_count;
    private int bay_count;
    private String option;
    private String security;
    private Date expiration_date;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
