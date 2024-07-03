package com.hollroom.user.controller;

import com.hollroom.user.config.GoogleConfig;
import com.hollroom.user.dto.GoogleDTO;
import com.hollroom.user.service.GoogleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleService googleService;

    private final GoogleConfig googleConfig;

    @GetMapping("/google")
    public void googleLogin(HttpServletResponse response) throws IOException{
        String url = "https://accounts.google.com/o/oauth2/auth?client_id=" + googleConfig.getClientId()
                + "&redirect_uri=" + googleConfig.getRedirectUri()
                + "&response_type=code"
                + "&scope=profile%20email";
        response.sendRedirect(url);
    }

    @GetMapping("/google/login")
    public ResponseEntity<?> googleCallback(@RequestParam("code") String code, HttpServletRequest request){
        String accessToken = googleService.getAccessToken(code);
        GoogleDTO googleDTO = googleService.getUserProfile(accessToken);
        log.info("GoogleDTO: {}", googleDTO);
        googleService.saveOrUpdateUser(googleDTO, request);
        String redirectUrl = "http://localhost:8090/hollroom/community/list?category=all&page=0";
        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }

}
