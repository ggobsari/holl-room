package com.hollroom.mypage.service;

import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.exception.UsernameNotFoundException;
import com.hollroom.mypage.repository.ProfileRepository;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //비밀번호 인코더
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
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

            // 로컬 정보 업데이트
            userEntity.setUserLocation(userSignupDTO.getUserLocation());
            // 비밀번호 업데이트
            if (userSignupDTO.getUserPassword() != null) {
                userEntity.setUserPassword(passwordEncoder.encode(userSignupDTO.getUserPassword()));
            }
            // 성별 정보 업데이트
            if(userSignupDTO.getUserGender() != null) {
                userEntity.setUserGender(userSignupDTO.getUserGender());
            }
            //생년월일 정보 업데이트
            if(userSignupDTO.getUserBirthday() != null) {
                userEntity.setUserBirthday(userSignupDTO.getUserBirthday());
            }
            // 실명 정보 업데이트
            if(userSignupDTO.getUserName() != null) {
                userEntity.setUserName(userSignupDTO.getUserName());
            }
            //핸드폰 정보 업데이트
            if(userSignupDTO.getUserPhoneNumber() != null){
                userEntity.setUserPhoneNumber(userSignupDTO.getUserPhoneNumber());
            }
            if(userSignupDTO.getUserNickname() != null){
                userEntity.setUserNickname(userSignupDTO.getUserNickname());
            }


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
            String fileUrl = "/hollroom/mypage/img/profile/" + image.getOriginalFilename();
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
            userEntity.setIsDeletedAt(new Date());//변수명 바꿀수도있음
            userRepository.save(userEntity);
            return true;
        }
    }

    //유저 탈퇴 is_deleted체크(비밀번호x)
    public boolean withdrawalWithoutPassword(UserSignupDTO userSignupDTO) {
        UserEntity userEntity = userRepository.findByUserEmail(userSignupDTO.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userEntity.setIsDeleted(true);
        userEntity.setIsDeletedAt(new Date());//변수명 바꿀수도있음
        userRepository.save(userEntity);
        return true;
    }

    //유저 닉네임 중복 검사
    public boolean isNicknameTaken(String nickname) {
        Optional<UserEntity> user = userRepository.findByUserNickname(nickname);
        return user.isPresent();
    }

    //유저 휴대폰 번호 중복 검사
    public boolean isPhoneNumTaken(String phoneNum) {
        Optional<UserEntity> user = profileRepository.findByUserPhoneNumber(phoneNum);
        return user.isPresent();
    }

    //이메일로 유저 정보 찾기
    public UserEntity findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userEntity;
    }
}










