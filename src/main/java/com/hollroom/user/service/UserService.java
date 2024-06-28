package com.hollroom.user.service;

import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.user.dto.UserLoginDTO;
import com.hollroom.user.dto.UserSignupDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import kotlinx.serialization.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    //================================================================================================================//
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    //================================================================================================================//
    public void signup(UserSignupDTO userSignupDTO){
//        // 비밀번호 빈칸 X
//        if(userSignupDTO.getUserPassword().isEmpty()){
//            throw new CheckApiException(ErrorCode.EMPTY_PASSWORD);
//        }

        String userEmail = userSignupDTO.getUserEmail();

        String userPassword = passwordEncoder.encode(userSignupDTO.getUserPassword());

        String userName = userSignupDTO.getUserName();

        String userNickname = userSignupDTO.getUserNickname();

        String userIntroduce = userSignupDTO.getUserIntroduce();

        String userPhoneNumber = userSignupDTO.getUserPhoneNumber();

        Date userBirthday = userSignupDTO.getUserBirthday();

        String userGender = userSignupDTO.getUserGender();

        String userLocation = userSignupDTO.getUserLocation();

        LocalDate userSignAt = LocalDate.now();

        Boolean userAdmin = false;

        Boolean ban = false;

        Boolean isDelete = false;

//        //이메일 중복 검사
//        Optional<UserEntity> userEmailDuplicate = userRepository.findByUserEmail(userSignupDTO.getUserEmail());
//
//        if(userEmailDuplicate.isPresent()){
//            throw new CheckApiException(ErrorCode.EXIST_EMAIL);
//        }

        UserEntity userEntity = new UserEntity(userEmail, userPassword, userName, userNickname,
                userIntroduce, userPhoneNumber, userBirthday, userGender, userLocation, userSignAt,
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
        //세션 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        session.setAttribute(userNickname, userEntity);

        log.info(session.toString());

        return session;
    }
    //================================================================================================================//
}
