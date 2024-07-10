package com.hollroom.mypage.controller;

import com.hollroom.mypage.domain.dto.MyRoommateDTO;
import com.hollroom.mypage.service.MywriteService;
import com.hollroom.roommate.dto.RoommateDTO;
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

import java.util.List;
import java.util.Map;


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

    //내가쓴글 컨트롤러
    @GetMapping("/mywrite")
    public String mywrite(HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/mywrite"; //interest 반환
    }

    //내가쓴글 목록 컨트롤러
    @GetMapping("/mypost")
    public ResponseEntity<?> getMyPosts(@RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "category", defaultValue = "all") String category,
                             HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        Long userId = user.getId();


        Map<String, Object> result = mywriteService.getPostsByUserIdAndCategory(userId, category, page);
        model.addAttribute("posts", result.get("posts"));
        model.addAttribute("totalPages", result.get("totalPages"));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //룸메이트 목록 컨트롤러
    @GetMapping("/mywrite_roommate")
    public String homePage(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        List<MyRoommateDTO> boardlist = mywriteService.selectMyRoommatesByUserId(user.getId());
        model.addAttribute("boardlist", boardlist);
        return "mypage/mywrite_roommate";
    }

    //월세매물 목록 컨트롤러
    @GetMapping("/mywrite_monthly")
    public String mywriteMonthly(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
//        List<MyRoommateDTO> boardlist = mywriteService.selectMyRoommatesByUserId(user.getId());
//        model.addAttribute("boardlist", boardlist);
        return "mypage/mywrite_monthly";
    }

//    //즐겨찾기 컬트롤러
//    @GetMapping("/interest")
//    public String interest(HttpSession session) {
//        if (!checkSession(session)) {
//            return "redirect:/login";
//        }
//        return "mypage/interest"; //interest 반환
//    }
}
