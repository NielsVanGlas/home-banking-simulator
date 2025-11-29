package com.niels.homebanking.dto.transaction;

import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;
import com.niels.homebanking.entity.TransactionStatus;
import com.niels.homebanking.factory.TransactionStatusFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShowTransactionDto {

    private UUID id;

    private String cause;

    private LocalDateTime dateTime;

    private ShowTransactionStatusDto status;

    private Double value;

    private boolean waiting;

    public ShowTransactionDto() {
    }

    public ShowTransactionDto(UUID id, String cause, LocalDateTime dateTime, ShowTransactionStatusDto status, Double value, boolean waiting) {
        this.id = id;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status;
        this.value = value;
        this.waiting = waiting;
    }

    public ShowTransactionDto(UUID id, String cause, LocalDateTime dateTime, TransactionStatus status, Double value, boolean waiting) {
        this.id = id;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = TransactionStatusFactory.showTransactionStatusDto(status);
        this.value = value;
        this.waiting = waiting;
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

    public ShowTransactionStatusDto getStatus() {
        return status;
    }

    public void setStatus(ShowTransactionStatusDto status) {
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
