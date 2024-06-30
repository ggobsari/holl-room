package com.hollroom.monthly.domain.entity;

import com.hollroom.community.domain.entity.AttachFileEntity;
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
    private Date expirationDate;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private Date deletedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AttachFileEntity attachEntity;

    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public MonthlyProductEntity(int deposit, int monthly, Long divisionCode, int floorCount, int pyeongCount, int roomCount, int bayCount, String roomOption, String securityFacility, Date expirationDate) {
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
    }
}
