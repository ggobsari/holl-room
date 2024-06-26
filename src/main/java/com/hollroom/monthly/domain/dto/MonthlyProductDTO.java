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
    private int divisionCode;
    private int floorCount;
    private int pyeongCount;
    private int roomCount;
    private int bayCount;
    private String roomOption;
    private String securityFacility;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
