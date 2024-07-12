package com.hollroom.user.controller;

import com.hollroom.user.config.KakaoConfig;
import com.hollroom.user.dto.KakaoDTO;
//import com.hollroom.user.entity.KakaoEntity;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.service.KakaoService;
import com.hollroom.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoController {
    //================================================================================================================//
    private final KakaoService kakaoService;
    //================================================================================================================//
    @GetMapping("/kakao")
    @ResponseBody
    public ResponseEntity<Void> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request){
        log.info("Received code: {}", code);
        String accessToken = kakaoService.getAccessToken(code);
        log.info("accessToken: {}", accessToken);
        KakaoDTO kakaoUser = kakaoService.getKakaoUserInfo(accessToken);
        kakaoService.saveKakaoUser(kakaoUser, request);
//        String redirectUrl = "http://localhost:8090/hollroom/";
        String redirectUrl = "http://175.45.205.226:8090/hollroom/";
        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }
}
