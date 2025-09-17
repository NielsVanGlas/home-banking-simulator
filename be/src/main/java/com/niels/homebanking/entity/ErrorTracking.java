package com.niels.homebanking.entity;

import com.niels.homebanking.entity.extra.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ErrorTracking extends CommonEntity {

    @Column
    private String errorMessage;

    @Column
    private HttpStatus status;

    public ErrorTracking() {
    }

    public ErrorTracking(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorTracking(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public ErrorTracking(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String errorMessage, HttpStatus status) {
        super(id, createdAt, updatedAt);
        this.errorMessage = errorMessage;
        this.status = status;
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
}
