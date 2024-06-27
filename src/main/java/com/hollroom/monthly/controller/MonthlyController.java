package com.hollroom.monthly.controller;

import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MonthlyController {
    private final MonthlyProductService productService;

    @GetMapping(value = {"/","/monthly"})
    public String showMainPage(Model model){
/*        List<MonthlyProductDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);*/
        return "monthly/monthly";
    }

    @GetMapping("/monthly/product")
    public String showProductPage(Model model){
        List<MonthlyProductDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

    @GetMapping("/monthly/product/register")
    public String showRegisterProductPage() {
        return "monthly/product_register";
    }

    @PostMapping("/monthly/product/register")
    public String registerProduct(MonthlyProductDTO product){
        productService.insertProduct(product);
        System.out.println(product);
        return "redirect:/monthly/product";
    }

    @GetMapping("/monthly/product/division/{divisionCode}")
    public String readDivisionProduct(Model model, @PathVariable int divisionCode){
        List<MonthlyProductDTO> productList = productService.readDivisionProduct(divisionCode);
        model.addAttribute("productList", productList);
        return "redirect:/monthly/product";
    }
}
