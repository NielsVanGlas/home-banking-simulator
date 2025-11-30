package com.niels.homebanking.dto.transaction;

import com.niels.homebanking.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ShowTransactionDto {

    private UUID id;

    private String cause;

    private LocalDateTime dateTime;

    private String status;

    private BigDecimal value;

    public ShowTransactionDto() {
    }

    public ShowTransactionDto(UUID id, String cause, LocalDateTime dateTime, String status, BigDecimal value) {
        this.id = id;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status;
        this.value = value;
    }

    public ShowTransactionDto(UUID id, String cause, LocalDateTime dateTime, TransactionStatus status, BigDecimal value) {
        this.id = id;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status.getStatus();
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
