package com.hollroom.admin.controller;

import com.hollroom.admin.domain.dto.AdminChartDTO;
import com.hollroom.admin.service.AdminChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminChartController {

    private final AdminChartService adminChartService;

    @GetMapping("/chart")
    public List<AdminChartDTO> getDailyStats(){
        log.info("Daily Stats: {}", adminChartService.getDailyStats());
        return adminChartService.getDailyStats();
    }
}
