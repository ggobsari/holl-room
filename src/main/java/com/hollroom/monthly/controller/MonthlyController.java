package com.hollroom.monthly.controller;

import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/monthly")
public class MonthlyController {
    private final MonthlyProductService productService;

    @GetMapping("/product")
    public String showMonthlyProduct(){
        return "monthly/product";
    }

}
