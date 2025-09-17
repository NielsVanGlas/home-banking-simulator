package com.niels.homebanking.dto.bankAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateBankAccountDto {

    private String name;

    private String iban;

    private UUID currency;

    private LocalDateTime balanceDate;

    private Double balance;

    public CreateBankAccountDto() {
    }

    public CreateBankAccountDto(String name, String iban, UUID currency, LocalDateTime balanceDate, Double balance) {
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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
