package com.niels.homebanking.dto.transaction;

import java.math.BigDecimal;

public class CreateTransactionDto {

    private String cause;

    private String status;

    private BigDecimal value;

    public CreateTransactionDto() {
    }

    public CreateTransactionDto(String cause, String status, BigDecimal value) {
        this.cause = cause;
        this.status = status;
        this.value = value;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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
