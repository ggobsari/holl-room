package com.hollroom.monthly.controller;

import com.hollroom.community.service.FileUploadService;
import com.hollroom.monthly.domain.dto.*;
import com.hollroom.monthly.service.MonthlyProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MonthlyController {
    private final MonthlyProductService productService;
    private final FileUploadService fileUploadService;
    private static final String MAIN_READ_TYPE="MAIN";

    @GetMapping(value = {"/","/monthly"})
    public String showMainPage(Model model){
        List<MonthlyProductResponseDTO> productList = productService.readProductAll();
        model.addAttribute("productList", productList);
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

    @GetMapping("/monthly/division")
    public String readDivision(Model model,@RequestParam(value = "address") String address,@RequestParam(value = "type") String type) {
        System.out.println("주소 : "+address);
        System.out.println("타입 : "+type);
        if(type.equals(MAIN_READ_TYPE)){
            DivisionDTO division = productService.readMainDivision(address);
            model.addAttribute("division", division);
        }else{
            List<DivisionDTO> divisionList= productService.readSubDivision(address);
            model.addAttribute("divisionList", divisionList);
        }
        return "monthly/division_list";
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
        //injectTempImgUrl(productList);
        model.addAttribute("productList", productList);
        return "monthly/product_list";
    }

    @GetMapping("/division/{addr}")
    @ResponseBody
    public DivisionDTO readDivision(@PathVariable String addr) {
        return productService.readMainDivision(addr);
    }
}
