package com.hollroom.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminChartDTO {

    private LocalDate date;

    private long dailyVisitor;

    private long countUser;

    private long roommateBoards;

    private long communityBoards;

}
