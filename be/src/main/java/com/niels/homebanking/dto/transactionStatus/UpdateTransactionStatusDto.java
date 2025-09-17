package com.niels.homebanking.dto.transactionStatus;

public class UpdateTransactionStatusDto {

    private String status;

    public UpdateTransactionStatusDto() {
    }

    public UpdateTransactionStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
