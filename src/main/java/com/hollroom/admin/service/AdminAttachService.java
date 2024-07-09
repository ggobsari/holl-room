package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.AdminAttachDTO;

import java.util.List;

public interface AdminAttachService {
    List<AdminAttachDTO> getAdminAttach(Long postId);
}
