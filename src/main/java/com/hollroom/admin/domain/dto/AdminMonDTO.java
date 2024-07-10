package com.hollroom.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("admin_monthly")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMonDTO {
    private int deposit;
    private int monthly;
    private Long Id;

    private Date created_At;
    private Date updated_At;
    private Date deleted_At;
    private Date expiration_date;
}
