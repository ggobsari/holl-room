package com.hollroom.mypage.controller;

import com.hollroom.mypage.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("user", profileService.getProfile());
        // mypage/profile.html 뷰를 반환
//        System.out.println(profileService.getProfile().getUserEmail());
//        System.out.println(profileService.getProfile().getUserNickname());
//        System.out.println(profileService.getProfile().getUserName());
//        System.out.println(profileService.getProfile().getUserPhoneNumber());
//        System.out.println(profileService.getProfile().getUserBirthday());
//        System.out.println(profileService.getProfile().getUserGender());
//        System.out.println(profileService.getProfile().getUserLocal());
        return "mypage/profile";
    }
}












