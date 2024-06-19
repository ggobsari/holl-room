package com.hollroom.monthly.domain.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyProductDTO {
    private long id;
    //private long user_id;
    private int deposit;
    private int monthly;
    private String address;
    private int floor_count;
    private int pyeong_count;
    private int room_count;
    private int bay_count;
    private String room_option;
    private String security_facility;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiration_date;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
