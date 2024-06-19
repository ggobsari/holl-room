package com.hollroom.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckApiException extends RuntimeException{ //RuntimeException을 상속받는 사용자

    private final ErrorCode errorCode;

}
