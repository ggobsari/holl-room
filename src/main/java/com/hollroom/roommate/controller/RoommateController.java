package com.hollroom.roommate.controller;

import com.hollroom.chatting.domain.entity.ChatRoom;
import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.roommate.service.RoommateService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@Slf4j
@RequiredArgsConstructor
@SessionAttributes
@RequestMapping("/roommate")
public class RoommateController {  // 1.9
    @Autowired
    RoommateService service;

    String userGender;

    @GetMapping("/test")
    public String test() {
        return "roommate/test";
    } // 삭제하기

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        userGender = user.getUserGender();
        List<RoommateDTO> boardlist = service.getBoardList(userGender);
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
        System.out.println(userGender + " =?= " + user.getUser_gender());
        if (userGender.equals(user.getUser_gender())) {
            System.out.println("열람 가능");
            model.addAttribute("accessible", true);
        } else {
            System.out.println("열람 불가!!!");
            model.addAttribute("accessible", false);
        }
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
    public String editPage(int roommate_id, HttpSession session, Model model) {
        System.out.println("################ edit Get 실행");
        RoommateDTO board = service.getBoard(roommate_id);
        System.out.println("edit board : " + board);
        model.addAttribute("board", board);
        model.addAttribute("mapping", "edit");
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
//        System.out.println(userGender + " =?= " + user.getUser_gender());
//        if (userGender.equals(user.getUser_gender())) {
//            System.out.println("열람 가능");
//            model.addAttribute("accessible", true);
//        } else {
//            System.out.println("열람 불가!!!");
//            model.addAttribute("accessible", false);
//        }
        if (board.getId() == user.getId()) {  // @@다른 방안 ??
            System.out.println("일치");
            model.addAttribute("accessible", true);
        } else {
            System.out.println("불일치!!!");
            model.addAttribute("accessible", false);
        }
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
        List<RoommateDTO> boardlist = service.getSearchResult(category, searchWord, userGender);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @PostMapping("/search")
    public String search(RoommateDTO data, Model model) {
        System.out.println("################ search Post 실행");
        System.out.println(data);
        List<RoommateDTO> boardlist = service.getFilteredResult(data, userGender);
        model.addAttribute("conditions", data);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @GetMapping("/checkUser")
    @ResponseBody
    public Map<String, String> checkUser(HttpSession session) {
        System.out.println("/checkUser OK");
        Map<String, String> map = new HashMap<>();
//        map.put("login", "true");

        // 유저가 로그인 상태인지 확인
        if(session.getAttribute("USER_NICKNAME") != null){
            System.out.println("######## 값: " + session.getAttribute("USER_NICKNAME"));
            UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
//            // 유저의 성별 정보가 있는지 확인
            if (user.getUserGender() == null) {
//                System.out.println("### gender: " + user.getUserGender());
                System.out.println("### gender 정보 없음");
                map.put("login", "true");
                map.put("gender", "false");
                return map;
            } else {
                System.out.println("### gender 정보 있음");
                map.put("login", "true");
                map.put("gender", "true");
                return map;
            }
        } else {
            System.out.println("######## 값: false");
            map.put("login", "false");
            return map;
        }

//        map.put("gender", "true");
//        return map;
    }
}
