package com.niels.homebanking.dto.authentication;


import java.io.Serializable;

public class ResendVerificationRequestDto implements Serializable {

    private String email;

    public ResendVerificationRequestDto() {
    }

    public ResendVerificationRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public ResendVerificationRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }
}