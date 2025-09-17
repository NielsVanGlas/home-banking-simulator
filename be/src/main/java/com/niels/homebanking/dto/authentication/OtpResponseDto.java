package com.niels.homebanking.dto.authentication;


import java.io.Serializable;

public class OtpResponseDto implements Serializable {

    private final String token;

    public OtpResponseDto(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

}