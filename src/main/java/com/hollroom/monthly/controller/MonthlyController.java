package com.hollroom.monthly.controller;

import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/monthly")
public class MonthlyController {
    private final MonthlyProductService productService;

    @GetMapping
    public String showMainPage(Model model){
        List<MonthlyProductDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
        return "monthly/monthly";
    }

    @GetMapping("/product")
    public String showProductPage(Model model){
        List<MonthlyProductDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
        return "monthly/product_list";
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
