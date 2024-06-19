package com.hollroom.user.service;

import com.hollroom.exception.CheckApiException;
import com.hollroom.exception.ErrorCode;
import com.hollroom.user.dto.UserDTO;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    //================================================================================================================//
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    //================================================================================================================//
    public void signup(UserDTO userDTO){
        // 비밀번호 빈칸 X
        if(userDTO.getUserPassword().isEmpty()){
            throw new CheckApiException(ErrorCode.EMPTY_PASSWORD);
        }

        String userEmail = userDTO.getUserEmail();

        String userPassword = passwordEncoder.encode(userDTO.getUserPassword());

        String userName = userDTO.getUserName();

        //이메일 중복 검사
        Optional<UserEntity> userEmailDuplicate = userRepository.findByUserEmail(userDTO.getUserEmail());

        if(userEmailDuplicate.isPresent()){
            throw new CheckApiException(ErrorCode.EXIST_EMAIL);
        }

        UserEntity userEntity = new UserEntity(userEmail, userPassword, userName);

        userRepository.save(userEntity);
    }
    //================================================================================================================//
    public String login(UserDTO userDTO) {

        String userEmail = userDTO.getUserEmail();

        String userPassword = userDTO.getUserPassword();

        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXIST_USER)
        );

        log.info(userEmail);

        if(!passwordEncoder.matches(userPassword, userEntity.getUserPassword())){
            throw new CheckApiException(ErrorCode.NOT_EQUAL_PASSWORD);
        }

        log.info(userPassword);

        return "로그인 성공!";
    }
}
