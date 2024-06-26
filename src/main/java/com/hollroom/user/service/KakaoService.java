package com.hollroom.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hollroom.user.config.KakaoConfig;
import com.hollroom.user.dto.KakaoDTO;
import com.hollroom.user.entity.KakaoEntity;
import com.hollroom.user.repository.KakaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    //================================================================================================================//
    private final KakaoRepository kakaoRepository;

    private final KakaoConfig kakaoConfig;
    //================================================================================================================//
    public KakaoEntity saveKakaoUser(KakaoDTO kakaoDTO){
        String email = kakaoDTO.getKakao_account().getEmail();
        KakaoEntity existingUser = kakaoRepository.findByKakaoUserEmail(email);

        if (existingUser != null) {// 기존 사용자 정보 업데이트 필요 시 업데이트 가능
            existingUser.setKakaoUserNickname(kakaoDTO.getKakao_account().getProfile().getNickname());
            return kakaoRepository.save(existingUser);
        }

        KakaoEntity kakaoEntity = new KakaoEntity();
        kakaoEntity.setKakaoUserEmail(email);
        kakaoEntity.setKakaoUserNickname(kakaoDTO.getKakao_account().getProfile().getNickname());
        kakaoEntity.setKakaoUserSignupAt(LocalDate.now());
        kakaoEntity.setKakaoUserAdmin(false);
        kakaoEntity.setKakaoUserBan(false);
        kakaoEntity.setKakaoUserIsDeleted(false);

        try {
            return kakaoRepository.save(kakaoEntity);
        } catch (Exception e){
            return null;
        }
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
