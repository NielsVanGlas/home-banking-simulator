package com.niels.homebanking.dto.authentication;

import java.io.Serializable;

public class AuthenticationRequestDto implements Serializable {

    private String username;
    private String password;

    public AuthenticationRequestDto() {

    }

    public AuthenticationRequestDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
