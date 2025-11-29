package com.niels.homebanking.dto.bankAccount;

import com.niels.homebanking.dto.currency.ShowCurrencyDto;
import com.niels.homebanking.entity.Currency;
import com.niels.homebanking.factory.CurrencyFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShowBankAccountDto {

    private UUID id;

    private String name;

    private String iban;

    private ShowCurrencyDto currency;

    private LocalDateTime balanceDate;

    private Double balance;

    public ShowBankAccountDto() {
    }

    public ShowBankAccountDto(UUID id, String name, String iban, ShowCurrencyDto currency, LocalDateTime balanceDate, Double balance) {
        this.id = id;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public ShowBankAccountDto(UUID id, String name, String iban, Currency currency, LocalDateTime balanceDate, Double balance) {
        this.id = id;
        this.name = name;
        this.iban = iban;
        this.currency = CurrencyFactory.showCurrencyDto(currency);
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public ShowCurrencyDto getShowCurrencyDto() {
        return currency;
    }

    public void setShowCurrencyDto(ShowCurrencyDto currency) {
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
