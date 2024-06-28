package com.hollroom.user.dto;

import lombok.Getter;

@Getter
public class ResetPasswordRequestDTO {
    private String email;
    private String newPassword;
    private String code;
}
