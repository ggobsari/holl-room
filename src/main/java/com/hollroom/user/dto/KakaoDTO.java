package com.hollroom.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class KakaoDTO {
    //================================================================================================================//
    private Long id;

    private String connected_at;

    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakao_account;

    //================================================================================================================//
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties{
        private String nickname;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount{

        @JsonProperty("profile_nickname_needs_agreement")
        private boolean profileNicknameNeedsAgreement;

        @Getter
        private Profile profile;

        @JsonProperty("has_email")
        private boolean hasEmail;

        @JsonProperty("email_needs_agreement")
        private boolean emailNeedsAgreement;

        @JsonProperty("is_email_valid")
        private boolean isEmailValid;

        @JsonProperty("is_email_verified")
        private boolean isEmailVerified;

        @Getter
        private String email;

        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class Profile{

            @Getter
            private String nickname;

            @JsonProperty("is_default_nickname")
            private boolean isDefaultNickname;

        }

    }

}
