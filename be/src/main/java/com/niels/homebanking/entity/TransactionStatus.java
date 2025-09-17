package com.niels.homebanking.entity;

import com.niels.homebanking.entity.extra.CommonEntity;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionStatus extends CommonEntity {

    @Column(nullable = false)
    private String status;

    public TransactionStatus() {
    }

    public TransactionStatus(String status) {
        this.status = status;
    }

    public TransactionStatus(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String status) {
        super(id, createdAt, updatedAt);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
