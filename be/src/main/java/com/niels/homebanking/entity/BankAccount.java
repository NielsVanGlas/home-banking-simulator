package com.niels.homebanking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.homebanking.entity.extra.CommonEntity;
import com.niels.homebanking.util.Encryptor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BankAccount extends CommonEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull()
    private UserAccount user;

    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String name;

    @Column(unique = true, nullable = false)
    @Convert(converter = Encryptor.class)
    private String iban;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "currency_id", nullable = false)
    @JsonBackReference
    private Currency currency;

    private LocalDateTime balanceDate;

    private BigDecimal balance;

    public BankAccount() {
    }

    public BankAccount(UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate) {
        this.user = user;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
    }

    public BankAccount(UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate, BigDecimal balance) {
        this.user = user;
        this.name = name;
        this.iban = iban;
        this.currency = currency;
        this.balanceDate = balanceDate;
        this.balance = balance;
    }

    public BankAccount(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, UserAccount user, String name, String iban, Currency currency, LocalDateTime balanceDate, BigDecimal balance) {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
