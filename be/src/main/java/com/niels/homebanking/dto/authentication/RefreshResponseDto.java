package com.niels.homebanking.dto.authentication;


import java.io.Serializable;

public record RefreshResponseDto(String accessToken) implements Serializable {

}