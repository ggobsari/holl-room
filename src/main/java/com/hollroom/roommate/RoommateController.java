package com.hollroom.roommate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roommate")
public class RoommateController {
    @GetMapping("/home")
    public String home() {
        return "roommate/roommate_home";
    }
}
