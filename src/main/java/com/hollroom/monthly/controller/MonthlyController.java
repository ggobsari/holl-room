package com.hollroom.monthly.controller;

import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/monthly")
public class MonthlyController {
    private final MonthlyProductService productService;

    @GetMapping("/product")
    public String showProductPage(){
        return "monthly/product";
    }

    @GetMapping("/product/register")
    public String showRegisterProductPage() {
        return "monthly/product_register";
    }

    @PostMapping("/product/register")
    public String registerProduct(MonthlyProductDTO product){
        productService.insertProduct(product);
        System.out.println(product);
        return "redirect:/monthly/product";
    }
}
