package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.CountUserDTO;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CountUserService {
    //================================================================================================================//
    private final UserRepository userRepository;
    //================================================================================================================//
    public CountUserDTO getCountUserForCurrentMonth(){
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.plusMonths(1).withDayOfMonth(1).minusDays(1);

        long newUsers = userRepository.countByUserSignupAtBetween(start, end);
        long deactivatedUsers = userRepository.countByBanTrueOrIsDeletedTrueAndUserSignupAtBetween(start, end);
        long totalUsers = userRepository.count();

        return new CountUserDTO(newUsers, deactivatedUsers, totalUsers);
    }
    //================================================================================================================//
}
