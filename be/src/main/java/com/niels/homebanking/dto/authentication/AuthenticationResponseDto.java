package com.niels.homebanking.dto.authentication;

import java.io.Serializable;

public class AuthenticationResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String username;
    private String error;

    public AuthenticationResponseDto(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public AuthenticationResponseDto(String error) {
        this.error = error;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getError() {
        return error;
    }
}
