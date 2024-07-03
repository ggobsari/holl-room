package com.hollroom.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hollroom.user.config.KakaoConfig;
import com.hollroom.user.dto.KakaoDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    //================================================================================================================//
//    private final KakaoRepository kakaoRepository;

    private final KakaoConfig kakaoConfig;

    private final UserRepository userRepository;
    //================================================================================================================//
    public HttpSession saveKakaoUser(KakaoDTO kakaoDTO, HttpServletRequest request){
        String email = kakaoDTO.getKakao_account().getEmail();

        Optional<UserEntity> optionalUser = userRepository.findByUserEmail(email);

        HttpSession session = request.getSession();

        if(optionalUser.isPresent()){
            // 사용자 정보가 이미 존재하면 로그인 성공을 반환하고 업데이트
            UserEntity existingUser = optionalUser.get();

            existingUser.setUserNickname(kakaoDTO.getKakao_account().getProfile().getNickname());

            userRepository.save(existingUser);

            session.setAttribute("USER_NICKNAME", existingUser);
        } else{
            UserEntity user = new UserEntity();
            user.setUserEmail(email);
            user.setUserNickname(kakaoDTO.getKakao_account().getProfile().getNickname());
            user.setUserName(null);
            user.setUserImage(null);
            user.setUserIntroduce(null);
            user.setUserPhoneNumber(null);
            user.setUserLocation(null);
            user.setUserBirthday(null);
            user.setUserSignupAt(LocalDate.now());
            user.setUserAdmin(false);
            user.setBan(false);
            user.setIsDeleted(false);
            user.setLoginType("kakao");

            userRepository.save(user);

            session.setAttribute("USER_NICKNAME", user);
        }

        return session;
    }
    //================================================================================================================//
    public String getAccessToken(String code){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoConfig.getClientId());
        params.add("redirect_uri", kakaoConfig.getRedirectUri());
        params.add("code", code);
        params.add("client_secret", kakaoConfig.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(kakaoConfig.getTokenUri(), request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error parsing access token", e);
        }
    }
    //================================================================================================================//
    public KakaoDTO getKakaoUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(kakaoConfig.getUserInfoUri(),
                HttpMethod.GET, entity, String.class);

        log.info("Kakao user info response: {}", response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(response.getBody(), KakaoDTO.class);
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error parsing user info", e);
        }
    }
}
