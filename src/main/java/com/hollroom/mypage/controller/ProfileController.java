package com.hollroom.mypage.controller;

import com.hollroom.mypage.service.ProfileService;
import com.hollroom.user.dto.UserLoginDTO;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    //프로필 정보 불러오기
    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("profile", profileService.getUserByEmail(user.getUserEmail()));
        return "mypage/profile";
    }

    //프로필 업데이트
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserSignupDTO userSignupDTO, HttpSession session) {
        try {
            boolean isUpdated = profileService.updateUserPassLocal(userSignupDTO);
            if (isUpdated) {

                //세션정보업데이트
                UserEntity updateUser = profileService.findUserByEmail(userSignupDTO.getUserEmail());
                if(updateUser != null) {
                    session.setAttribute("USER_NICKNAME", updateUser);
                }

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
    public ResponseEntity<?> updateUserInfo(@RequestBody UserSignupDTO userSignupDTO, HttpSession session) {
        try {
            boolean isUpdated = profileService.updateUserInfo(userSignupDTO);
            if (isUpdated) {

                //세션정보업데이트
                UserEntity updateUser = profileService.findUserByEmail(userSignupDTO.getUserEmail());
                if(updateUser != null) {
                    session.setAttribute("USER_NICKNAME", updateUser);
                }

                return ResponseEntity.ok().body(Collections.singletonMap("message", "프로필이 성공적으로 업데이트되었습니다."));
            } else {
                return ResponseEntity.status(500).body(Collections.singletonMap("message", "프로필 업데이트에 실패했습니다."));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "서버 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    //사진 업데이트
    @PostMapping("/uploadProfileImage")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("image") MultipartFile image, @RequestPart("profile") UserSignupDTO userSignupDTO, HttpSession session) throws IOException {
        // Service 호출하여 이미지 업로드 및 프로필 업데이트 처리
        profileService.saveProfileImage(image, userSignupDTO);

        //세션정보업데이트
        UserEntity updateUser = profileService.findUserByEmail(userSignupDTO.getUserEmail());
        if(updateUser != null) {
            session.setAttribute("USER_NICKNAME", updateUser);
        }

        return ResponseEntity.ok().body("이미지 업로드 성공");
    }

    //닉네임 중복검사
    @GetMapping("/checkNickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isTaken = profileService.isNicknameTaken(nickname);
        return new ResponseEntity<>(isTaken, HttpStatus.OK);
    }

    //휴대폰 번호 중복검사
    @GetMapping("/checkPhoneNum")
    public ResponseEntity<Boolean> checkPhoneNum(@RequestParam String phoneNum) {
        boolean isTaken = profileService.isPhoneNumTaken(phoneNum);
        return new ResponseEntity<>(isTaken, HttpStatus.OK);
    }
}














