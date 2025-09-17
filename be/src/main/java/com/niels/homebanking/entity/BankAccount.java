package com.niels.homebanking.entity;

import com.niels.homebanking.entity.extra.CommonEntity;
import com.niels.homebanking.util.Encryptor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BankAccount extends CommonEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull()
    private UserAccount user;

    @Column(unique = true, nullable = false)
    @Convert(converter = Encryptor.class)
    private String name;

    @Column(unique = true, nullable = false)
    @Convert(converter = Encryptor.class)
    private String iban;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    private LocalDateTime balanceDate;

    private Double balance;

    public BankAccount() {
    }

    public BankAccount(UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate, Double balance) {
        this.user = user;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public BankAccount(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate, Double balance) {
        super(id, createdAt, updatedAt);
        this.user = user;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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
