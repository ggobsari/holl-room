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
    private static final String MAIN_READ_TYPE="MAIN";

    @GetMapping(value = {"/","/monthly"})
    public String showMainPage(Model model){
        List<MonthlyProductDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
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

    @GetMapping("/monthly/division")
    public String readDivision(Model model,@RequestParam String address,@RequestParam String type) {
        if(type.equals(MAIN_READ_TYPE)){
            DivisionDTO division = productService.readMainDivision(address);
            model.addAttribute("division", division);
        }else{
            List<DivisionDTO> divisionList= productService.readSubDivision(address);
            model.addAttribute("divisionList", divisionList);
        }
        return "";
    }

    @PostMapping("/monthly/product/register")
    public String registerProduct(MonthlyProductDTO product){
        productService.insertProduct(product);
        System.out.println(product);
        return "redirect:/monthly/product";
    }

    @GetMapping("/monthly/product/division/{addr}")
    public String readDivisionProduct(Model model, @PathVariable String addr){
        List<MonthlyProductDTO> productList = productService.readDivisionProduct(addr);
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

    @GetMapping("/division/{addr}")
    @ResponseBody
    public DivisionDTO readDivision(@PathVariable String addr) {
        return productService.readMainDivision(addr);
    }
}
