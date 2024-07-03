package com.hollroom.monthly.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyTrendDTO {
    private Long id;
    private Long divisionCode;
    private String priceAvg;
    private int dateCode;
}
