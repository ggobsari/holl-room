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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 인코더 추가

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
            userEntity.setUserLocation(profileDTO.getUserLocation());

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

            // 로컬 정보 업데이트
            userEntity.setUserIntroduce(profileDTO.getUserIntroduce());

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return false;
        }
    }

    private static final String UPLOAD_DIR = "src/main/resources/static/mypage/img/profile/";
    //사진 업데이트
    public void saveProfileImage(MultipartFile image, ProfileDTO profileDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(profileDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 업로드 디렉토리 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일 저장
            Path filePath = Paths.get(UPLOAD_DIR, image.getOriginalFilename());
//            System.out.println(filePath);
            Files.write(filePath, image.getBytes());
            String fileUrl = "http://localhost:8090/hollroom/mypage/img/profile/" + image.getOriginalFilename();
            userEntity.setUserImage(fileUrl);

            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
        }

    }

}










