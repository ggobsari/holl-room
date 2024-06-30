package com.hollroom.monthly.domain.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyProductDTO {
    private long id;
    //private long user_id;
    private String imgUrl;
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
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    private MultipartFile roomImg;

    public MonthlyProductDTO(long id, String imgUrl, int deposit, int monthly, Long divisionCode, int floorCount, int pyeongCount, int roomCount, int bayCount, String roomOption, String securityFacility, Date expirationDate, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.deposit = deposit;
        this.monthly = monthly;
        this.divisionCode = divisionCode;
        this.floorCount = floorCount;
        this.pyeongCount = pyeongCount;
        this.roomCount = roomCount;
        this.bayCount = bayCount;
        this.roomOption = roomOption;
        this.securityFacility = securityFacility;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
