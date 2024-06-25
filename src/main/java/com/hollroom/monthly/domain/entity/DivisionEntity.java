package com.hollroom.monthly.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "division")
public class DivisionEntity {
    @Id
    @GeneratedValue
    public int id;
    public int topDivisionId;
    public int mainDivisionNum;
    public String name;
}
