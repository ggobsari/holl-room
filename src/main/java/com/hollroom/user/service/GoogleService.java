package com.hollroom.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hollroom.user.config.GoogleConfig;
import com.hollroom.user.dto.GoogleDTO;
//import com.hollroom.user.entity.GoogleEntity;
import com.hollroom.user.entity.UserEntity;
//import com.hollroom.user.repository.GoogleRepository;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GoogleService {
    //================================================================================================================//
//    private final GoogleRepository googleRepository;

    private final GoogleConfig googleConfig;

    private final HttpSession httpSession;

    private final UserRepository userRepository;
    //================================================================================================================//
    public String getAccessToken(String code){
        String tokenUrl = "https://oauth2.googleapis.com/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleConfig.getClientId());
        body.add("client_secret", googleConfig.getClientSecret());
        body.add("code", code);
        body.add("redirect_uri", googleConfig.getRedirectUri());
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            return node.get("access_token").asText();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //================================================================================================================//
    public GoogleDTO getUserProfile(String accessToken){
        String profileUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(profileUrl, HttpMethod.GET, request, String.class);

        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), GoogleDTO.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //================================================================================================================//
    public HttpSession saveOrUpdateUser(GoogleDTO googleDTO, HttpServletRequest request){
        Optional<UserEntity> optionalUser = userRepository.findByUserEmail(googleDTO.getEmail());

        UserEntity user;

        if(optionalUser.isPresent()){
            user = optionalUser.get();
        } else {
            user = new UserEntity();
            user.setUserNickname(null);
            user.setUserImage(null);
            user.setUserIntroduce(null);
            user.setUserPhoneNumber(null);
            user.setUserBirthday(null);
            user.setUserGender(null);
            user.setUserLocation(null);
            user.setUserSignupAt(LocalDate.now());
            user.setUserAdmin(false);
            user.setBan(false);
            user.setIsDeleted(false);
            user.setLoginType("google");
        }

        user.setUserEmail(googleDTO.getEmail());
        user.setUserName(googleDTO.getName());

        userRepository.save(user);

        HttpSession session = request.getSession();

        session.setAttribute("USER_NICKNAME", user);

        log.info(String.valueOf(session));

        return session;

    }
}
