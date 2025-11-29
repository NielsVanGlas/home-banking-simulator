package com.niels.homebanking.dto.bankAccount;

import java.util.UUID;

public class CreateBankAccountDto {

    private String name;

    private UUID currency;

    private Double balance;

    public CreateBankAccountDto() {
    }

    public CreateBankAccountDto(String name, UUID currency, Double balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
