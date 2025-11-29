package com.niels.homebanking.dto.authentication;


import java.io.Serializable;

public record OtpResponseDto(String token) implements Serializable {

}