package com.hollroom.user.service;

import com.hollroom.admin.service.DailyVisitorService;
import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.user.dto.UserLoginDTO;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    //================================================================================================================//
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final DailyVisitorService dailyVisitorService;
    //================================================================================================================//
    public void signup(UserSignupDTO userSignupDTO){

        String userEmail = userSignupDTO.getUserEmail();

        String userPassword = passwordEncoder.encode(userSignupDTO.getUserPassword());

        String userName = userSignupDTO.getUserName();

        String userNickname = userSignupDTO.getUserNickname();

        String userIntroduce = userSignupDTO.getUserIntroduce();

        String userPhoneNumber = userSignupDTO.getUserPhoneNumber();

        Date userBirthday = userSignupDTO.getUserBirthday();

        String userGender = userSignupDTO.getUserGender();

        String userLocation = userSignupDTO.getUserLocation();

        String loginType = "basic";

        LocalDate userSignAt = LocalDate.now();

        Boolean userAdmin = false;

        Boolean ban = false;

        Boolean isDelete = false;

        UserEntity userEntity = new UserEntity(userEmail, userPassword, userName, userNickname,
                userIntroduce, userPhoneNumber, userBirthday, userGender, userLocation, loginType, userSignAt,
                userAdmin, ban, isDelete);

        userRepository.save(userEntity);
    }
    //================================================================================================================//
    public Optional<UserEntity> isNicknameDuplicate(String userNickname){
        return userRepository.findByUserNickname(userNickname);
    }
    //================================================================================================================//
    public Optional<UserEntity> isEmailAlreadyExists(String userEmail){
        return userRepository.findByUserEmail(userEmail);
    }

    //================================================================================================================//
    public HttpSession login(UserLoginDTO userLoginDTO, HttpServletRequest request) {

        final String userNickname = "USER_NICKNAME";

        String userEmail = userLoginDTO.getUserEmail();

        String userPassword = userLoginDTO.getUserPassword();

        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXIST_USER)
        );

        if(!passwordEncoder.matches(userPassword, userEntity.getUserPassword())){
            throw new CheckApiException(ErrorCode.NOT_EQUAL_PASSWORD);
        }

        if(userEntity.getIsDeleted() || userEntity.getBan()){
            throw new CheckApiException(ErrorCode.BAN_USER);
        }
        //세션 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        session.setAttribute(userNickname, userEntity);

        dailyVisitorService.logVisitor(userEntity.getId());

        log.info(session.toString());
        System.out.println(session.toString());

        return session;
    }
    //================================================================================================================//
    public Optional<UserEntity> findByUserEmail(String email){
        return userRepository.findByUserEmail(email);
    }
    //================================================================================================================//
    public String createPasswordResetToken(UserEntity user){
        String code = UUID.randomUUID().toString();
        user.setResetToken(code);
        userRepository.save(user);
        return code;
    }
    //================================================================================================================//
    public boolean verifyResetToken(String code){
        Optional<UserEntity> optionalUser = userRepository.findByResetToken(code);
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            user.setResetToken(null);
            userRepository.save(user);
            return true;
        } else{
            return false;
        }
    }
    //================================================================================================================//
    public void updatePassword(String email, String newPassword){
        Optional<UserEntity> optionalUser = findByUserEmail(email);
        log.info(String.valueOf(optionalUser));
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            user.setUserPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);  // 토큰을 다시 null로 설정
            userRepository.save(user);
        } else{
            log.info("가입되어 있지 않은 회원입니다.");
        }
    }
}
