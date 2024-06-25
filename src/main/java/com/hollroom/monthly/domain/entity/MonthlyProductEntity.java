package com.hollroom.monthly.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monthly_product")
public class MonthlyProductEntity {
    @Id
    @GeneratedValue
    private long id;
//    @ManyToOne
//    private UserEntity user;
    private int deposit;
    private int monthly;
    private int majorClassify;
    private int middleClassify;
    private int smallClassify;
    private int floorCount;
    private int pyeongCount;
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

    public MonthlyProductEntity(int deposit, int monthly, int majorClassify,int middleClassify,int smallClassify, int floorCount, int pyeongCount, int roomCount, int bayCount, String roomOption, String securityFacility, Date expirationDate) {
        this.deposit = deposit;
        this.monthly = monthly;
        this.majorClassify = majorClassify;
        this.middleClassify = middleClassify;
        this.smallClassify = smallClassify;
        this.floorCount = floorCount;
        this.pyeongCount = pyeongCount;
        this.roomCount = roomCount;
        this.bayCount = bayCount;
        this.roomOption = roomOption;
        this.securityFacility = securityFacility;
        this.expirationDate = expirationDate;
    }
}
