package com.niels.homebanking.dto.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ShowErrorTracking {

    private String errorMessage;

    private HttpStatus status;

    private LocalDateTime createdAt;

    public ShowErrorTracking() {
    }

    public ShowErrorTracking(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ShowErrorTracking(String errorMessage, HttpStatus status, LocalDateTime createdAt) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
