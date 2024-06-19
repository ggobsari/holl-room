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
@Table(name = "monthly_products")
public class MonthlyProductEntity {
    @Id
    @GeneratedValue
    private long id;
//    @ManyToOne
//    private UserEntity user;
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
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;
    private Date deleted_at;
}
