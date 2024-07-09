package com.hollroom.admin.dao;

import com.hollroom.admin.domain.dto.AdminAttachDTO;

import java.util.List;

public interface AdminAttachDAO {
    List<AdminAttachDTO> getAdminAttach(Long postId);
}
