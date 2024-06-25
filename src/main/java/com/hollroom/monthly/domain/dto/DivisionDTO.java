package com.hollroom.monthly.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DivisionDTO {
    public int id;
    public int topDivision;
    public int mainDivision;
    public String name;
}
