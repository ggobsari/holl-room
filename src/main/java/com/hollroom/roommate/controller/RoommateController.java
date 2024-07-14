package com.hollroom.roommate.controller;

import com.hollroom.roommate.dto.RoommateDTO;
import com.hollroom.roommate.dto.RoommateUserDTO;
import com.hollroom.roommate.service.RoommateService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoommateController {  // 2.0
    @Autowired
    RoommateService service;
    String myGender;
    Long myId;

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        myGender = user.getUserGender();
        List<RoommateDTO> boardlist = service.getBoardList(myGender);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @GetMapping("/detail")
    public String detailPage(int roommate_id, Model model, HttpSession session) {
        UserEntity me = (UserEntity) session.getAttribute("USER_NICKNAME");
        myGender = me.getUserGender();
        RoommateDTO board = service.getBoardById(roommate_id);
        RoommateUserDTO user = service.getUserInfo(board.getId());
        model.addAttribute("board", board);
        model.addAttribute("user", user);
        if (myGender.equals(user.getUser_gender())) {
            model.addAttribute("accessible", true);
        } else {
            model.addAttribute("accessible", false);
        }
        return "roommate/roommate_detail";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "roommate/roommate_register";
    }

    @PostMapping("/register")
    public String register(RoommateDTO board) {
        int result = service.register(board);
        return "redirect:/roommate/home";
    }

    @GetMapping("/edit")
    public String editPage(int roommate_id, HttpSession session, Model model) {
        RoommateDTO board = service.getBoard(roommate_id);
        model.addAttribute("board", board);
        model.addAttribute("mapping", "edit");
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if (board.getId() == user.getId()) {
            model.addAttribute("accessible", true);
        } else {
            model.addAttribute("accessible", false);
        }
        return "roommate/roommate_register";
    }

    @PostMapping("/edit")
    public String edit(RoommateDTO board) {
        int result = service.editBoard(board);
        return "redirect:/roommate/home";
    }

    @GetMapping("/delete")
    public String deleteBoard(int roommate_id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        myId = user.getId();
        RoommateDTO board = service.getBoard(roommate_id);
//        System.out.println(myId + " ,, " + board.getId());
        if (myId == board.getId()) {
//            System.out.println("동일 아이디 삭제 가능");
            int result = service.deleteBoard(roommate_id);
        }
        return "redirect:/roommate/home";
    }

    @GetMapping("/search")
    public String search(String category, String searchWord, Model model) {
        List<RoommateDTO> boardlist = service.getSearchResult(category, searchWord, myGender);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @PostMapping("/search")
    public String search(RoommateDTO data, Model model) {
        List<RoommateDTO> boardlist = service.getFilteredResult(data, myGender);
        model.addAttribute("conditions", data);
        model.addAttribute("boardlist", boardlist);
        return "roommate/roommate_home";
    }

    @GetMapping("/checkUser")
    @ResponseBody
    public Map<String, String> checkUser(HttpSession session) {
        Map<String, String> map = new HashMap<>();
        // 유저가 로그인 상태인지 확인
        if(session.getAttribute("USER_NICKNAME") != null){
            UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
//            // 유저의 성별 정보가 있는지 확인
            if (user.getUserGender() == null) {
                map.put("login", "true");
                map.put("gender", "false");
                return map;
            } else {
                map.put("login", "true");
                map.put("gender", "true");
                return map;
            }
        } else {
            map.put("login", "false");
            return map;
        }
    }
}