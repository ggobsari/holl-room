package com.hollroom.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDTO {

    private String id;

    private String email;

    private String name;

    private String picture;

}
