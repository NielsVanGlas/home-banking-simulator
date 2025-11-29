package com.niels.homebanking.dto.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateTransactionDto {

    private String cause;

    private LocalDateTime dateTime;

    private UUID status;

    private Double value;

    private boolean waiting;

    public UpdateTransactionDto() {
    }

    public UpdateTransactionDto(String cause, LocalDateTime dateTime, UUID status, Double value, boolean waiting) {
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status;
        this.value = value;
        this.waiting = waiting;
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

    public UUID getStatus() {
        return status;
    }

    public void setStatus(UUID status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
    
}
