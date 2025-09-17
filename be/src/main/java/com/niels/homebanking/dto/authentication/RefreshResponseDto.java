package com.niels.homebanking.dto.authentication;


import java.io.Serializable;

public class RefreshResponseDto implements Serializable {

    private final String accessToken;

    public RefreshResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

}