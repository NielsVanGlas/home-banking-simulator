package com.niels.homebanking.dto.currency;

import java.util.UUID;

public class ShowCurrencyDto {

    private UUID id;

    private String name;

    private String iso;

    private String symbol;

    private int exchange;

    public ShowCurrencyDto() {
    }

    public ShowCurrencyDto(UUID id, String name, String iso, String symbol, int exchange) {
        this.id = id;
        this.name = name;
        this.iso = iso;
        this.symbol = symbol;
        this.exchange = exchange;
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

    public int getExchange() {
        return exchange;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
    }
}
