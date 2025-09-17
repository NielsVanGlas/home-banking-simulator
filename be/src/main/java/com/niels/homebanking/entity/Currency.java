package com.niels.homebanking.entity;

import com.niels.homebanking.entity.extra.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Currency extends CommonEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String iso;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private long exchange;

    //
    public Currency() {
    }

    public Currency(String name, String iso, String symbol, long exchange) {
        this.name = name;
        this.iso = iso;
        this.symbol = symbol;
        this.exchange = exchange;
    }

    public Currency(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String iso, String symbol, long exchange) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.iso = iso;
        this.symbol = symbol;
        this.exchange = exchange;
    }

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getExchange() {
        return exchange;
    }

    public void setExchange(long exchange) {
        this.exchange = exchange;
    }

}
