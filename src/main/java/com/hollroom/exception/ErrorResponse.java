package com.hollroom.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse { //exception 발생 시 응답
    //================================================================================================================//
    private String message;

    private int errorCode;
    //================================================================================================================//
    public ErrorResponse(ErrorCode errorCode){
        this.message = errorCode.getErrorMessage();
        this.errorCode = errorCode.getErrorCode();
    }
}
