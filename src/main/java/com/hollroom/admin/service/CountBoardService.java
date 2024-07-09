package com.hollroom.admin.service;

import com.hollroom.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CountBoardService {
    //================================================================================================================//
    private final CommunityRepository communityRepository;

    private final AdminRoomService adminRoomService;
    //================================================================================================================//
    public Map<String, Long> getTotalBoards(){
        long communityBoards = communityRepository.count();
        long roommateBoards = adminRoomService.selectAllAdminRoommates().size();
        long totalBoards = communityBoards + roommateBoards;

        Map<String, Long> counts = new HashMap<>();
        counts.put("communityBoards", communityBoards);
        counts.put("roommateBoards", roommateBoards);
        counts.put("totalBoards", totalBoards);

        return counts;
    }
    //================================================================================================================//
}
