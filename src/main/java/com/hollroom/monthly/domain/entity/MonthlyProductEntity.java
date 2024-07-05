package com.hollroom.monthly.domain.entity;

import com.hollroom.monthly.domain.dto.MonthlyProductRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monthly_product")
public class MonthlyProductEntity {
    @Id
    @GeneratedValue
    private Long id;
//    @ManyToOne
//    private UserEntity user;
    private int deposit;
    private int monthly;
    private Long divisionCode;
    private int floorCount;
    private Float pyeongCount;
    private int roomCount;
    private int bayCount;
    private String roomOption;
    private String securityFacility;
    private Date expirationDate;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private Date deletedAt;

    public MonthlyProductEntity(int deposit, Long divisionCode, int monthly, int floorCount, Float pyeongCount, int roomCount, int bayCount, String roomOption, String securityFacility, Date expirationDate) {
        this.deposit = deposit;
        this.divisionCode = divisionCode;
        this.monthly = monthly;
        this.floorCount = floorCount;
        this.pyeongCount = pyeongCount;
        this.roomCount = roomCount;
        this.bayCount = bayCount;
        this.roomOption = roomOption;
        this.securityFacility = securityFacility;
        this.expirationDate = expirationDate;
    }

    public MonthlyProductEntity(MonthlyProductRequestDTO dto) {
        this.deposit = dto.getDeposit();
        this.divisionCode = dto.getDivisionCode();
        this.monthly = dto.getMonthly();
        this.floorCount = dto.getFloorCount();
        this.pyeongCount = dto.getPyeongCount();
        this.roomCount = dto.getRoomCount();
        this.bayCount = dto.getBayCount();
        this.roomOption = dto.getRoomOption();
        this.securityFacility = dto.getSecurityFacility();
        this.expirationDate = dto.getExpirationDate();
    }
}
