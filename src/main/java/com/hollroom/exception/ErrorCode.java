package com.hollroom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode { //에러코드 정리
    //================================================================================================================//
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, 400, "존재하는 이메일입니다."),

    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호를 입력하세요."),

    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, 400, "존재하지 않는 사용자입니다."),

    NOT_EQUAL_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호가 일치하지 않습니다."),

    BAN_USER(HttpStatus.BAD_REQUEST, 400, "접근 제한된 유저입니다.");
    //================================================================================================================//
    private final HttpStatus httpStatus;

    private final int errorCode;

    private final String errorMessage;

}
