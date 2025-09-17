package com.niels.homebanking.dto.authentication;

import java.io.Serializable;

public class RefreshRequestDto implements Serializable {

    private String refreshToken;

    public RefreshRequestDto() {
    }

    public RefreshRequestDto(String refreshToken) {
        this.setRefreshToken(refreshToken);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

    }

}