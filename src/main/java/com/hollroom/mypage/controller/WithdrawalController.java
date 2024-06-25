package com.hollroom.mypage.controller;

import com.hollroom.mypage.service.ProfileService;
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

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "mypage/withdrawal";  // mypage/withdrawal.html 뷰를 반환
    }

    // 비밀번호 확인
    @PostMapping("/verifypassword")
    public ResponseEntity<Map<String, Object>> verifyPassword(@RequestParam("password") String password) {
        Long userId = 1L; // 실제 구현에서는 현재 로그인된 사용자의 ID를 사용해야 함
        System.out.println(userId);
        boolean isPasswordCorrect = profileService.checkPassword(userId, password);
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isPasswordCorrect);
        response.put("userId", userId);
        return ResponseEntity.ok(response);
    }

    // 회원 탈퇴
    @PostMapping("/deleteuser")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId) {
        profileService.deactivateUser(userId);
        return ResponseEntity.ok("User deactivated successfully");
    }

}
