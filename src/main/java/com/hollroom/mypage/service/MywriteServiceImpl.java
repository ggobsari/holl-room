package com.hollroom.mypage.service;

import com.hollroom.admin.domain.dto.AdminComDTO;
import com.hollroom.mypage.dao.MywriteDAO;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MywriteServiceImpl implements MywriteService {
    private final MywriteDAO mywriteDAO;


    @Override
    public List<AdminComDTO> myCommunityList(Long id) {
        return mywriteDAO.myCommunityList(id);
    }

    @Override
    public List<AdminComDTO> myCommunityListCategory(AdminComDTO adminComDTO) {
        return mywriteDAO.myCommunityListCategory(adminComDTO);
    }

    @Override
    public List<MyRoommateDTO> selectMyRoommatesByUserId(Long userId) {
        return mywriteDAO.selectMyRoommateById(userId);
    }
}



