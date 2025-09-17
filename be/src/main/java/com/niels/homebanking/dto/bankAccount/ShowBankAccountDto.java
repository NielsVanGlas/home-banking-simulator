package com.niels.homebanking.dto.bankAccount;

import com.niels.homebanking.dto.currency.ShowCurrencyDto;
import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;
import com.niels.homebanking.entity.Currency;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.factory.CurrencyFactory;
import com.niels.homebanking.factory.UserAccountFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShowBankAccountDto {

    private UUID id;

    private ShowUserAccountDto user;

    private String name;

    private String iban;

    private ShowCurrencyDto currency;

    private LocalDateTime balanceDate;

    private Double balance;

    public ShowBankAccountDto() {
    }

    public ShowBankAccountDto(UUID id, ShowUserAccountDto user, String name, String iban, ShowCurrencyDto currency, LocalDateTime balanceDate, Double balance) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public ShowBankAccountDto(UUID id, UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate, Double balance) {
        this.id = id;
        this.user = UserAccountFactory.showUserAccountDto(user);
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

    public ShowUserAccountDto getUser() {
        return user;
    }

    public void setUser(ShowUserAccountDto user) {
        this.user = user;
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
