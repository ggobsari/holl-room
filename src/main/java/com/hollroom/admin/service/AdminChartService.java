package com.hollroom.admin.service;

//import com.hollroom.admin.RoommateMapper;
import com.hollroom.admin.domain.dto.AdminChartDTO;
import com.hollroom.admin.repository.DailyVisitorRepository;
import com.hollroom.community.domain.entity.CommunityEntity;
import com.hollroom.community.repository.CommunityRepository;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminChartService {
    //================================================================================================================//
    private final DailyVisitorRepository dailyVisitorRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    private final AdminRoomService adminRoomService;

//    private final RoommateMapper roommateMapper;
    //================================================================================================================//
    public List<AdminChartDTO> getDailyStats(){

        List<AdminChartDTO> stats = new ArrayList<>();

        List<LocalDate> dates = getRecentDates(30);

        for (LocalDate localDate : dates) {
            long dailyVisitorCount = dailyVisitorRepository.countDailyVisitorByVisitDate(localDate);
            long newUsers = userRepository.countByUserSignupAtAndIsDeletedFalseAndBanFalse(localDate);
//            long roommateBoards = roommateMapper.countByDate(date);
//            List<CommunityEntity> communityEntities = communityRepository.countByCreatedAt(date);
            long communityBoards = communityRepository.countByCreatedAt(localDate);

//            List<LocalDate> communityBoards = communityEntities.stream()
//                    .map(communityEntity -> convertToLocalDateViaInstant(communityEntity.getCreatedAt()))
//                    .toList();

            AdminChartDTO stat = new AdminChartDTO();
            stat.setDate(localDate);
            stat.setDailyVisitor(dailyVisitorCount);
            stat.setCountUser(newUsers);
//            stat.setRoommateBoards(roommateBoards);
            stat.setCommunityBoards(communityBoards);

            stats.add(stat);

        }
            return stats;
        }
    //================================================================================================================//
    private List<LocalDate> getRecentDates(int days){
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < days; i++) {
            dates.add(currentDate.minusDays(i));
        }
        return dates;
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate convertToLocalDateViaInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}



