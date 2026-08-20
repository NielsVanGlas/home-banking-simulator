package com.niels.homebanking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.homebanking.entity.extra.CommonEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction extends CommonEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    BankAccount account;

    @Column(nullable = false)
    private String cause;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "status_id", nullable = false)
    @JsonBackReference
    private TransactionStatus status;

    @Column(nullable = false)
    private BigDecimal value;

    public Transaction() {
    }

    public Transaction(BankAccount account, String cause, LocalDateTime dateTime, TransactionStatus status, BigDecimal value) {
        this.account = account;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status;
        this.value = value;
    }

    public Transaction(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, BankAccount account, String cause, LocalDateTime dateTime, TransactionStatus status, BigDecimal value) {
        super(id, createdAt, updatedAt);
        this.account = account;
        this.cause = cause;
        this.dateTime = dateTime;
        this.status = status;
        this.value = value;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
