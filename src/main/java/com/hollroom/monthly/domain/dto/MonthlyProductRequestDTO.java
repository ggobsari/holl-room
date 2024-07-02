package com.hollroom.monthly.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyProductRequestDTO {
    //private long user_id;
    private MultipartFile roomImg;
    private int deposit;
    private int monthly;
    private Long divisionCode;
    private int floorCount;
    private int pyeongCount;
    private int roomCount;
    private int bayCount;
    private String roomOption;
    private String securityFacility;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
}
