package com.hollroom.roommate.controller;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.roommate.service.RoommateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
public class RoommateController {  // 1.2
    @Autowired
    RoommateService service;

    @GetMapping("/test")
    public String test() {
        return "roommate/test";
    }
//    @GetMapping("/jdbc/test")
//    public String jdbcTest() {
//        System.out.println("*************개수 : " + dao.templateCount());
//        return "redirect:/";
//    }
//    @GetMapping("/mybatis/test")
//    public String mybatisTest() {
//        List<RoommateDTO> boardlist = dao.select();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(boardlist.get(i));
//        }
//        return "redirect:/";
//    }

    @GetMapping("/home")
    public String homePage(Model model) {
        List<RoommateDTO> boardlist = service.getBoardList();
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @GetMapping("/detail")
    public String detailPage(int roommate_id, Model model, HttpSession session) {
        System.out.println("################ detail 실행");
        System.out.println(roommate_id);
        RoommateDTO board = service.getBoardById(roommate_id);
        System.out.println(board);
//        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        RoommateUserDTO user = service.getUserInfo(board.getId());
        System.out.println(user);
        model.addAttribute("board", board);
        model.addAttribute("user", user);
        return "roommate/roommate_detail";
    }

    @GetMapping("/register")
    public String registerPage() {  // Model model
        return "roommate/roommate_register";
    }

    @PostMapping("/register")
    public String register(RoommateDTO board) {
        System.out.println("################ register Post 실행");
        System.out.println(board);
        int result = service.register(board);
        System.out.println("################ " + result + " 개 등록 성공");
        return "redirect:/roommate/home";
    }

    @GetMapping("/edit")
    public String editPage(int roommate_id, Model model) {
        System.out.println("################ edit Get 실행");
        RoommateDTO board = service.getBoard(roommate_id);
        System.out.println("edit board : " + board);
        model.addAttribute("board", board);
        model.addAttribute("mapping", "edit");
        return "roommate/roommate_register";
    }

    @PostMapping("/edit")
    public String edit(RoommateDTO board) {
        System.out.println("################ edit Post 실행");
        System.out.println(board);
        int result = service.editBoard(board);
        System.out.println("################ " + result + " 개 수정 성공");
        return "redirect:/roommate/home";
    }

    @GetMapping("/delete")
    public String deleteBoard(int roommate_id) {
        System.out.println("################ delete 실행");
        System.out.println(roommate_id);
        int result = service.deleteBoard(roommate_id);
        System.out.println("################ " + result + " 개 삭제 성공");
        return "redirect:/roommate/home";
    }

    @GetMapping("/search")
    public String search(String category, String searchWord, Model model) {
        System.out.println("################ search Get 실행");
        System.out.println("category : " + category);
        System.out.println("searchWord : " + searchWord);
        List<RoommateDTO> boardlist = service.getSearchResult(category, searchWord);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @PostMapping("/search")
    public String search(RoommateDTO data, Model model) {
        System.out.println("################ search Post 실행");
        System.out.println(data);
        List<RoommateDTO> boardlist = service.getFilteredResult(data);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

//    //chattingController??
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
