package com.hollroom.mypage.service;

import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.exception.UsernameNotFoundException;
import com.hollroom.mypage.dto.ProfileDTO;
import com.hollroom.user.dto.UserDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 인코더 추가

    //    // 예시용 임시 메서드, 실제로는 DB나 다른 소스에서 데이터를 가져옴
//    public ProfileDTO getProfile() {
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setUserNickname("닉네임");
//        profileDTO.setUserEmail("user@example.com");
//        profileDTO.setUserName("실명");
//
//        String birthdateStr = "1990-01-01";
//        Date birthdate = parseDate(birthdateStr);
//        profileDTO.setUserBirthday(birthdate);
//
//        profileDTO.setUserGender(Boolean.FALSE);
//        profileDTO.setUserLocal("서울");
//
//        return profileDTO;
//    }

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByEmail(String email) { //프로필 불러오기
        UserEntity userEntity = userRepository.findByUserEmail(email).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXIST_USER)
        );
        return new ProfileDTO(userEntity);
    }

    // 프로필 업데이트
    public boolean updateUserPassLocal(ProfileDTO profileDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(profileDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 비밀번호 업데이트
            if (profileDTO.getUserPassword() != null && !profileDTO.getUserPassword().isEmpty()) {
                userEntity.setUserPassword(passwordEncoder.encode(profileDTO.getUserPassword()));
            }

            // 로컬 정보 업데이트
            userEntity.setUserLocal(profileDTO.getUserLocal());

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return false;
        }
    }

    // Info 업데이트
    public boolean updateUserInfo(ProfileDTO profileDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(profileDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            //자기소개 업데이트
            userEntity.setUserInfo(profileDTO.getUserInfo());

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return false;
        }
    }

    //사진 업데이트
    public void saveProfileImage(String fileUrl, ProfileDTO profileDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(profileDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            userEntity.setUserImage(fileUrl);

            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
        }

    }


}










