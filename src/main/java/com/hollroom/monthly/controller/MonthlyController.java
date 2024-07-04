package com.hollroom.monthly.controller;

import com.hollroom.community.service.FileUploadService;
import com.hollroom.monthly.domain.dto.*;
import com.hollroom.monthly.service.DivisionService;
import com.hollroom.monthly.service.MonthlyProductService;
import com.hollroom.monthly.service.MonthlyTrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MonthlyController {
    private final MonthlyProductService productService;
    private final MonthlyTrendService trendService;
    private final DivisionService divisionService;

    @GetMapping(value = {"/","/monthly"})
    public String showMainPage(Model model){
        List<MonthlyProductResponseDTO> productList = productService.readProductAll();
        Map<Long,MonthlyTrendDTO> trendMap = trendService.readMonthlyTrendsByAddress("서울시");
        model.addAttribute("productList", productList);
        model.addAttribute("trendMap", trendMap);
        return "monthly/monthly";
    }

    @GetMapping("/monthly/product")
    public String showProductPage(Model model){
        List<MonthlyProductResponseDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

    @GetMapping("/monthly/product/register")
    public String showRegisterProductPage(Model model) {
        List<DivisionDTO> divisionList = divisionService.readSubDivisions("서울시");
        model.addAttribute("divisionList", divisionList);
        return "monthly/product_register";
    }



    @PostMapping("/monthly/product/register")
    public String registerProduct(MonthlyProductRequestDTO product){
        productService.insertProduct(product);
        return "redirect:/monthly/product";
    }

    @GetMapping("/monthly/product/division/{addr}")
    public String readDivisionProduct(Model model, @PathVariable String addr){
        List<MonthlyProductResponseDTO> productList = productService.readDivisionProduct(addr);
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

}
