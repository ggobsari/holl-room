package com.hollroom.monthly.controller;

import com.hollroom.community.service.FileUploadService;
import com.hollroom.monthly.domain.dto.*;
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

    @GetMapping(value = {"/","/monthly"})
    public String showMainPage(Model model){
        List<MonthlyProductResponseDTO> productList = productService.readProductAll();
        System.out.println("에러1");
        Map<Long,MonthlyTrendDTO> trendMap = trendService.readMonthlyTrendsByAddress("서울시");
        System.out.println("에러2");
        System.out.println("트렌드 맵 사이즈 == "+trendMap.size());
        System.out.println( "프로덕트 리스트 사이즈 == "+productList.size());
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
    public String showRegisterProductPage() {
        return "monthly/product_register";
    }



    @PostMapping("/monthly/product/register")
    public String registerProduct(MonthlyProductRequestDTO product){
        productService.insertProduct(product);
        return "redirect:/monthly/product";
    }

    @GetMapping("/monthly/product/division/{addr}")
    public String readDivisionProduct(Model model, @PathVariable String addr){
        System.out.println(addr);
        List<MonthlyProductResponseDTO> productList = productService.readDivisionProduct(addr);
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

}
