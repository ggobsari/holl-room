package com.hollroom.admin.domain.dto;

import com.hollroom.common.TabType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("admin_attach")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAttachDTO {
    private Long file_Id;
    private String file_Original_Name;
    private String file_Store_Name;
    private Long post_Id;
    private TabType tabType;
}
