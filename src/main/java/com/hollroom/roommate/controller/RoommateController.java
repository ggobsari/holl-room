package com.hollroom.roommate.controller;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.service.RoommateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
//@Slf4j
@RequiredArgsConstructor
@SessionAttributes
@RequestMapping("/roommate")
public class RoommateController {
    @Autowired
    RoommateService service;

    @GetMapping("/test")
    public String test() {
        return "roommate/test";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<RoommateDTO> boardlist = service.getBoardList();
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "roommate/roommate_register";
    }

    @PostMapping("/register")
    public String register(RoommateDTO board) {
        System.out.println("################ register 실행");
        System.out.println(board);
        int result = service.register(board);
        System.out.println("################ " + result + " 개 등록 성공");
        return "redirect:/roommate/home";
    }

    @GetMapping("/edit")
    public String editPage(Model model) {
        RoommateDTO board = service.getBoard(1);
        System.out.println(board);
        model.addAttribute("board", board);
        model.addAttribute("mapping", "edit");
        return "roommate/roommate_edit";
    }
//
//    @PostMapping("/edit")
//    public String edit(RoommateDTO board) {
//        System.out.println("################ edit 실행");
//        System.out.println(board);
//        int result = service.editBoard(board);
//        System.out.println("################ " + result + " 개 수정 성공");
//        return "redirect:/roommate/home";
//    }
//
//    @GetMapping("/delete")
//    public String delete(int roommate_id) {
//        System.out.println("################ delete 실행");
//        System.out.println(roommate_id);
//        int result = service.deleteBoard(board);
//        System.out.println("################ " + result + " 개 삭제 성공");
//        return "redirect:/roommate/home";
//    }
//    public int deleteBoard(int roommate_id) {
//        return dao.delete(roommate_id);
//    }
//    public int deleteee(int roommate_id) {
//        return sessionTemplate.deletee("com.hollroom.roommate.delete", roommate_id);
//    }

//    @GetMapping("/search")
//    public String search() {
//
//    }
//
//    @GetMapping("/search")
//    public String search(@ char nocturnal, char smoking, char pet, String sleeping_habits) {
//        List<RoommateDTO> boardlist = service.search(nocturnal, smoking, pet, sleeping_habits);
//        return "";
//    }
//    public List<RoommateDTO> search(...) {
//        char noct, ... ;
//        if (nocturnal == null) {
//            noct = ;
//        }
//        //char[] habitsArr = sleeping_habits.toCharArr();
//        return dao.search(...);
//    }
//    public List<RoommateDTO> search(...) {
//
//    }
//
//    //chattinController로??
//    @GetMapping("/chat")
//    public String chatPage() {
//        return "/roommate/roommate_chat";
//    }
//
//    @PostMapping("/chat")
//    public String chat(ChattingDTO message) {
//        System.out.println("################ chat 실행")
//        System.out.println(message);
//        return "///";
//    }
}
