package com.hollroom.mypage.controller;

import com.hollroom.admin.domain.dto.AdminComDTO;
import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import com.hollroom.mypage.service.MywriteService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MywriteController {
    @Autowired
    private MywriteService mywriteService;

    // 세션 체크 헬퍼 메서드
    private boolean checkSession(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        return user != null;
    }

    //내가쓴글(커뮤니티) 컨트롤러
    @GetMapping("/mywrite")
    public String mywrite(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/mywrite"; //interest 반환
    }

    // 커뮤니티 목록 컨트롤러
    @GetMapping("/myCommunityList")
    public ResponseEntity<?> myCommunityList(Model model, @RequestParam("category") String category, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        Long userId = user.getId();

        List<AdminComDTO> boardlist = new ArrayList<>();
        if(category.equals("all")) {
            boardlist = mywriteService.myCommunityList(userId);
        }else{
            AdminComDTO adminComDTO = new AdminComDTO();
            adminComDTO.setCategory(category);
            adminComDTO.setUser_Id(userId);
            boardlist = mywriteService.myCommunityListCategory(adminComDTO);
        }
        model.addAttribute("posts", boardlist);
        model.addAttribute("totalPosts", boardlist.size()); //총 게시물 개수
        model.addAttribute("totalPages", boardlist.size()/20); //총 게시물 페이지 수

        model.addAttribute("category", category);

        return new ResponseEntity<>(boardlist, HttpStatus.OK);
    }

    //내가쓴글(룸메이트) 컨트롤러
    @GetMapping("/mywrite_roommate")
    public String mywrite_roommate(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/mywrite_roommate"; //interest 반환
    }

    //룸메이트 목록 컨트롤러
    @GetMapping("/myRoommateList")
    public ResponseEntity<?> homePage(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        List<MyRoommateDTO> boardlist = mywriteService.selectMyRoommatesByUserId(user.getId()); //전체 목록
        model.addAttribute("posts", boardlist);
        model.addAttribute("totalPosts", boardlist.size());
        model.addAttribute("totalPages", boardlist.size()/20);

        return new ResponseEntity<>(boardlist, HttpStatus.OK);
    }

    //월세매물 목록 컨트롤러
//    @GetMapping("/mywrite_monthly")
//    public String mywriteMonthly(Model model, HttpSession session) {
//        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
//        List<MyRoommateDTO> boardlist = mywriteService.selectMyRoommatesByUserId(user.getId());
//        model.addAttribute("boardlist", boardlist);
//        return "mypage/mywrite_monthly";
//    }

//    //즐겨찾기 컬트롤러
//    @GetMapping("/interest")
//    public String interest(HttpSession session) {
//        if (!checkSession(session)) {
//            return "redirect:/login";
//        }
//        return "mypage/interest"; //interest 반환
//    }
}
