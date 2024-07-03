package com.hollroom.monthly.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_trend")
public class MonthlyTrendEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long divisionCode;
    private String priceAvg;
    private int dateCode;
}