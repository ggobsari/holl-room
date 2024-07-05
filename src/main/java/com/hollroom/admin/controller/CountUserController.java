package com.hollroom.admin.controller;

import com.hollroom.admin.dto.CountUserDTO;
import com.hollroom.admin.service.CountUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CountUserController {

    private final CountUserService countUserService;

    @GetMapping("/stats/current-month")
    public CountUserDTO getCountUserForCurrentMonth(){
        return countUserService.getCountUserForCurrentMonth();
    }
}
