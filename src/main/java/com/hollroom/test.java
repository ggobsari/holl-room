package com.hollroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

    @GetMapping("/admin/index")
    public String test(){
        return "admin/dashboard";
    }

}
