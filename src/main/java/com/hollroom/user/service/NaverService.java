package com.hollroom.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hollroom.user.config.NaverConfig;
import com.hollroom.user.dto.NaverDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NaverService {
    //================================================================================================================//
//    private final NaverRepository naverRepository;

    private final NaverConfig naverConfig;

    private final HttpSession httpSession;

    private final UserRepository userRepository;

    //================================================================================================================//
    public String generateState() {
        return UUID.randomUUID().toString();
    }

    //================================================================================================================//
    public String getAccessToken(String code, String state) {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + naverConfig.getClientId()
                + "&client_secret=" + naverConfig.getClientSecret() + "&code=" + code + "&state=" + state;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, null, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            return node.get("access_token").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //================================================================================================================//
    public NaverDTO getUserProfile(String accessToken) {
        String profileUrl = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(profileUrl, HttpMethod.GET, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), NaverDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //================================================================================================================//
    public void saveOrUpdateUser(NaverDTO naverDTO) {

        Optional<UserEntity> optionalUser = userRepository.findByUserEmail(naverDTO.getResponse().getEmail());

        UserEntity user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else{
            user = new UserEntity();
            user.setUserImage(null);
            user.setUserIntroduce(null);
            user.setUserPhoneNumber(null);
            user.setUserLocation(null);
            user.setUserSignupAt(LocalDate.now());
            user.setUserAdmin(false);
            user.setBan(false);
            user.setIsDeleted(false);
            user.setLoginType("naver");
        }

        user.setUserEmail(naverDTO.getResponse().getEmail());
        user.setUserName(naverDTO.getResponse().getName());
        user.setUserNickname(naverDTO.getResponse().getNickname());

        //생년월일 date타입으로 변환
        String birthdayString = naverDTO.getResponse().getBirthyear() + "-" + naverDTO.getResponse().getBirthday();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthday = format.parse(birthdayString);
            user.setUserBirthday(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        user.setUserGender(naverDTO.getResponse().getGender());

        userRepository.save(user);

//        NaverEntity user = naverRepository.findByNaverUserEmail(naverDTO.getResponse().getEmail());
//
//        if(user == null){
//            user = new NaverEntity();
//            user.setNaverUserImage(null);
//            user.setNaverUserIntroduce(null);
//            user.setNaverUserPhoneNumber(null);
//            user.setNaverUserLocation(null);
//            user.setNaverUserSignupAt(LocalDate.now());
//            user.setNaverUserAdmin(false);
//            user.setNaverUserBan(false);
//            user.setNaverUserIsDeleted(false);
//        }
//
//        user.setNaverUserEmail(naverDTO.getResponse().getEmail());
//        user.setNaverUserName(naverDTO.getResponse().getName());
//        user.setNaverUserNickname(naverDTO.getResponse().getNickname());
//
//        //생년월일 date타입으로 변환
//        String birthdayString = naverDTO.getResponse().getBirthyear() + "-" + naverDTO.getResponse().getBirthday();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date birthday = format.parse(birthdayString);
//            user.setNaverUserBirthday(birthday);
//        }catch (ParseException e){
//            throw new RuntimeException(e);
//        }
//
//        user.setNaverUserGender(naverDTO.getResponse().getGender());
//
//        naverRepository.save(user);

        httpSession.setAttribute("user", user);
    }
}
