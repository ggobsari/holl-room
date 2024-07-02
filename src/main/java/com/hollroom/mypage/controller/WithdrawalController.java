package com.hollroom.mypage.controller;

import com.hollroom.mypage.service.ProfileService;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@org.springframework.stereotype.Controller
@RequestMapping("/mypage")
public class WithdrawalController {

    @Autowired
    private ProfileService profileService;

    // 세션 체크 헬퍼 메서드
    private boolean checkSession(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        return user != null;
    }

    //회원탈퇴 페이지 컨트롤러
    @GetMapping("/withdrawal")
    public String interest(HttpSession session){
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        return "mypage/withdrawal";  // mypage/withdrawal 페이지 반환
    }

    //회원탈퇴 컨트롤러
    @PostMapping("/handleWithdrawal")
    public ResponseEntity<Map<String, Object>> handleWithdrawal(@RequestBody Map<String, String> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (!checkSession(session)) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.ok(response);
        }

        String password = request.get("password");
        UserEntity loggedInUser = (UserEntity) session.getAttribute("USER_NICKNAME");
        UserSignupDTO user = profileService.getUserByEmail(loggedInUser.getUserEmail());

        try {
            boolean success = profileService.withdrawal(user, password);
            if (success) {
                response.put("success", true);
                response.put("message", "회원탈퇴가 성공적으로 처리되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "비밀번호가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원탈퇴 처리에 실패했습니다.");
        }

        return ResponseEntity.ok(response);
    }
}

