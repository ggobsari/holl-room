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
}
