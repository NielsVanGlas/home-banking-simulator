package com.niels.homebanking.dto.bankAccount;


import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateBankAccountDto {

    private UUID currency;

    private LocalDateTime balanceDate = LocalDateTime.now();

    private Double balance;

    public UpdateBankAccountDto() {
    }

    public UpdateBankAccountDto(UUID currency, LocalDateTime balanceDate, Double balance) {
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public LocalDateTime getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDateTime balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
