package com.niels.homebanking.dto.transactionStatus;

public class CreateTransactionStatusDto {

    private String status;

    public CreateTransactionStatusDto() {
    }

    public CreateTransactionStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
