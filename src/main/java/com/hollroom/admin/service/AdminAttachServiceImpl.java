package com.hollroom.admin.service;

import com.hollroom.admin.dao.AdminAttachDAO;
import com.hollroom.admin.domain.dto.AdminAttachDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAttachServiceImpl implements  AdminAttachService {
    private final AdminAttachDAO adminAttachDAO;

    @Override
    public List<AdminAttachDTO> getAdminAttach(Long postId) {
        return adminAttachDAO.getAdminAttach(postId);
    }
}
