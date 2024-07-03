package com.hollroom.user.controller;

import com.hollroom.user.config.NaverConfig;
import com.hollroom.user.dto.NaverDTO;
import com.hollroom.user.service.NaverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NaverController {
    //================================================================================================================//
    private final NaverService naverService;

    private final NaverConfig naverConfig;
    //================================================================================================================//
    @GetMapping("/naver")
    public void naverLogin(HttpServletResponse response) throws IOException {
        String state = naverService.generateState();
        String url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + naverConfig.getClientId()
                + "&redirect_uri=" + naverConfig.getRedirectUri() + "&state=" + state;
        response.sendRedirect(url);
    }

    @GetMapping("/login")
    public ResponseEntity<?> naverCallback(@RequestParam("code") String code, @RequestParam("state") String state,
                                           HttpServletRequest request){
        log.info(code);
        log.info(state);
        String accessToken = naverService.getAccessToken(code, state);
        NaverDTO naverDTO = naverService.getUserProfile(accessToken);
        log.info(naverDTO.toString());
        naverService.saveOrUpdateUser(naverDTO, request);
        String redirectUrl = "http://localhost:8090/hollroom/community/list?category=all&page=0";
        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }
}
