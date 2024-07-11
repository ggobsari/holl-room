package com.hollroom.admin.controller;

import com.hollroom.admin.service.DailyVisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DailyVisitorController {
    //================================================================================================================//
    private final DailyVisitorService dailyVisitorService;
    //================================================================================================================//
    @GetMapping("/visitor/today")
    public long getTodayVisitorsCount(){
        return dailyVisitorService.getTodayVisitorCount();
    }

    @GetMapping("/visitor/increase-rate")
    public double getIncreaseRate(){
        return dailyVisitorService.getIncreaseRate();
    }
}
