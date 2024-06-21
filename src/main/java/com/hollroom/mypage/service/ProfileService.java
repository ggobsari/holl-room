package com.hollroom.mypage.service;

import com.hollroom.mypage.dto.ProfileDTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProfileService {

    // 예시용 임시 메서드, 실제로는 DB나 다른 소스에서 데이터를 가져옴
    public ProfileDTO getProfile() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserNickname("닉네임");
        profileDTO.setUserEmail("user@example.com");
        profileDTO.setUserName("실명");

        String birthdateStr = "1990-01-01";
        Date birthdate = parseDate(birthdateStr);
        profileDTO.setUserBirthday(birthdate);

        profileDTO.setUserGender(Boolean.FALSE);
        profileDTO.setUserLocal("서울");

        return profileDTO;
    }

    // 문자열로부터 Date 객체를 생성하는 메서드
    private Date parseDate(String dateStr) {
        System.out.println("Parsing date: " + dateStr);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}










