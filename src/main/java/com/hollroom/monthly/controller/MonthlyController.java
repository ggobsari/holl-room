package com.hollroom.monthly.controller;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyProductDTO;
import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/monthly/division?topDivision={topDivision}&mainDivision={mainDivision}")
    public String readMainDivision(Model model,@RequestParam(value = "topDivision") String topDivision, @RequestParam(value = "mainDivision") String mainDivision) {
        DivisionDTO division = productService.readMainDivision(topDivision,mainDivision);
        model.addAttribute("division", division);
        return "";
    }

    @GetMapping("/monthly/division/{topDivision}")
    public String readSubDivision(Model model,@PathVariable String topDivision) {
        List<DivisionDTO> divisionList = productService.readSubDivision(topDivision);
        model.addAttribute("divisionList", divisionList);
        return "";
    }

    @PostMapping("/monthly/product/register")
    public String registerProduct(MonthlyProductDTO product){
        productService.insertProduct(product);
        System.out.println(product);
        return "redirect:/monthly/product";
    }

    @GetMapping("/monthly/product/division/{division}")
    public String readDivisionProduct(Model model, @PathVariable String division){
        List<MonthlyProductDTO> productList = productService.readDivisionProduct(division);
        model.addAttribute("productList", productList);
        return "redirect:/monthly/product";
    }
}
