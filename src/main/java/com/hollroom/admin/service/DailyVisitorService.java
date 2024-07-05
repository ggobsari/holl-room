package com.hollroom.admin.service;

import com.hollroom.admin.entity.DailyVisitor;
import com.hollroom.admin.repository.DailyVisitorRepository;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DailyVisitorService {
    //================================================================================================================//
    private final DailyVisitorRepository dailyVisitorRepository;

    private final UserRepository userRepository;
    //================================================================================================================//
    public void logVisitor(Long userId){
        LocalDate today = LocalDate.now();
        UserEntity user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));

        //이미 오늘 로그인했던 유저일 경우 체크
        List<DailyVisitor> todayLogs = dailyVisitorRepository.findByVisitDateAndUser(today, user);

        if(todayLogs.isEmpty()){
            DailyVisitor log = new DailyVisitor(user, today);
            dailyVisitorRepository.save(log);
        }
    }
    //================================================================================================================//
    public long getTodayVisitorCount(){
        LocalDate today = LocalDate.now();
        List<DailyVisitor> logs = dailyVisitorRepository.findByVisitDate(today);
        return logs.stream()
                .map(DailyVisitor::getUser)
                .distinct()
                .count();
    }
    //================================================================================================================//
    public long getYesterdayVisitorsCount(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DailyVisitor> logs = dailyVisitorRepository.findByVisitDate(yesterday);
        return logs.stream()
                .map(DailyVisitor::getUser)
                .distinct()
                .count();
    }
    //================================================================================================================//
    public double getIncreaseRate(){
        long todayCount = getTodayVisitorCount();
        long yesterdayCount = getYesterdayVisitorsCount();
        if(yesterdayCount == 0){
            return todayCount > 0 ? 100 : 0;
        }
        return ((double) (todayCount - yesterdayCount) / yesterdayCount) * 100;
    }
}
