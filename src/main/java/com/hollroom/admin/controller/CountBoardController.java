package com.hollroom.admin.controller;

import com.hollroom.admin.service.CountBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CountBoardController {

    private final CountBoardService countBoardService;

    @GetMapping("/boards")
    public Map<String, Long> getBoardsCount(){
        return countBoardService.getTotalBoards();
    }
}
