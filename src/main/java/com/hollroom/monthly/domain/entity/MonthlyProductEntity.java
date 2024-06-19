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
    private String address;
    private int floor_count;
    private int pyeong_count;
    private int room_count;
    private int bay_count;
    private String room_option;
    private String security_facility;
    private Date expiration_date;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;
    private Date deleted_at;

    public MonthlyProductEntity(int deposit, int monthly, String address, int floor_count, int pyeong_count, int room_count, int bay_count, String room_option, String security_facility, Date expiration_date) {
        this.deposit = deposit;
        this.monthly = monthly;
        this.address = address;
        this.floor_count = floor_count;
        this.pyeong_count = pyeong_count;
        this.room_count = room_count;
        this.bay_count = bay_count;
        this.room_option = room_option;
        this.security_facility = security_facility;
        this.expiration_date = expiration_date;
    }
}
