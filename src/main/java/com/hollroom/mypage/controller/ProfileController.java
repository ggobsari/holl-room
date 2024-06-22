package com.hollroom.mypage.controller;

import com.hollroom.mypage.dto.ProfileDTO;
import com.hollroom.mypage.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collections;

@Controller
@RequestMapping("/mypage")
public class ProfileController {

    private final ProfileService profileService;

    // ProfileService를 주입받는 생성자
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // 프로필 페이지를 반환하는 메서드
    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        // 모델에 사용자 프로필 정보를 추가
        // 로그인된 사용자 ID를 가져오는 로직 (예: SecurityContextHolder에서 가져오기)
        String loggedInUserId = "1111@1111"; // 실제 로그인된 사용자 ID로 변경 필요
        ProfileDTO user = (ProfileDTO) profileService.getUserByEmail(loggedInUserId);
        model.addAttribute("user", user);
        // mypage/profile.html 뷰를 반환
//        System.out.println(user.getUserEmail());
//        System.out.println(user.getUserNickname());
//        System.out.println(user.getUserName());
//        System.out.println(user.getUserPhoneNumber());
//        System.out.println(user.getUserBirthday());
//        System.out.println(user.getUserGender());
//        System.out.println(user.getUserLocal());
//        System.out.println(user.getUserInfo());
//        System.out.println(user.getUserImage());
        return "mypage/profile";
    }

    //프로필 업데이트
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody ProfileDTO profileDTO) {
        try {
            boolean isUpdated = profileService.updateUserPassLocal(profileDTO);
            if (isUpdated) {
                return ResponseEntity.ok().body(Collections.singletonMap("message", "프로필이 성공적으로 업데이트되었습니다."));
            } else {
                return ResponseEntity.status(500).body(Collections.singletonMap("message", "프로필 업데이트에 실패했습니다."));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    //자기소개 업데이트
    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody ProfileDTO profileDTO) {
        try {
            boolean isUpdated = profileService.updateUserInfo(profileDTO);
            if (isUpdated) {
                return ResponseEntity.ok().body(Collections.singletonMap("message", "프로필이 성공적으로 업데이트되었습니다."));
            } else {
                return ResponseEntity.status(500).body(Collections.singletonMap("message", "프로필 업데이트에 실패했습니다."));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }


//    사진 업데이트
@PostMapping("/uploadProfileImage")
public ResponseEntity<?> uploadProfileImage(@RequestParam("image") MultipartFile image, @RequestPart("profile") ProfileDTO profileDTO) {
    // Service 호출하여 이미지 업로드 및 프로필 업데이트 처리
    profileService.saveProfileImage(image, profileDTO);
    return ResponseEntity.ok().body("이미지 업로드 성공");
}
}













