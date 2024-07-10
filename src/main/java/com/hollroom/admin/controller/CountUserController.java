package com.hollroom.admin.controller;

import com.hollroom.admin.domain.dto.CountUserDTO;
import com.hollroom.admin.service.CountUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountUserController {

    private final CountUserService countUserService;

    @GetMapping("/stats/current-month")
    public CountUserDTO getCountUserForCurrentMonth(){
        return countUserService.getCountUserForCurrentMonth();
    }
}
