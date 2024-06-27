package com.hollroom.mypage.service;

import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.exception.UsernameNotFoundException;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //비밀번호 인코더
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //프로필 불러오기
    public UserSignupDTO getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByUserEmail(email).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXIST_USER)
        );
        return new UserSignupDTO(userEntity);
    }

    // 사용자 패스워드 및 지역정보 업데이트
    public boolean updateUserPassLocal(UserSignupDTO userSignupDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(userSignupDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 비밀번호 업데이트
            if (userSignupDTO.getUserPassword() != null) {
                userEntity.setUserPassword(passwordEncoder.encode(userSignupDTO.getUserPassword()));
            }

            // 로컬 정보 업데이트
            userEntity.setUserLocation(userSignupDTO.getUserLocation());

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return false;
        }
    }

    // Info 업데이트
    public boolean updateUserInfo(UserSignupDTO userSignupDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(userSignupDTO.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 로컬 정보 업데이트
            userEntity.setUserIntroduce(userSignupDTO.getUserIntroduce());

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 서버 콘솔에 에러 출력
            return false;
        }
    }

    //이미지 저장 업데이트
    private static final String UPLOAD_DIR = "src/main/resources/static/mypage/img/profile/";
    public void saveProfileImage(MultipartFile image, UserSignupDTO userSignupDTO) {
        try {
            UserEntity userEntity = userRepository.findByUserEmail(userSignupDTO.getUserEmail())
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

    //유저 탈퇴 is_deleted체크
    public boolean withdrawal(UserSignupDTO userSignupDTO, String password) {
        UserEntity userEntity = userRepository.findByUserEmail(userSignupDTO.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, userEntity.getUserPassword())) {
            return false;
        } else {
            userEntity.setIsDeleted(true);
            userRepository.save(userEntity);
            return true;
        }
    }
}










