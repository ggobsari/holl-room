package com.hollroom.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    private String userEmail;

    private String userPassword;
}
