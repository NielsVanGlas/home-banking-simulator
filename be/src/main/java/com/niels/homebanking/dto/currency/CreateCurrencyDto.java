package com.niels.homebanking.dto.currency;

import java.math.BigDecimal;

public class CreateCurrencyDto {

    private String name;

    private String iso;

    private String symbol;

    private BigDecimal exchange;

    public CreateCurrencyDto() {

    }

    public CreateCurrencyDto(String name, String iso, String symbol, BigDecimal exchange) {
        this.name = name;
        this.iso = iso;
        this.symbol = symbol;
        this.exchange = exchange;
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

    public BigDecimal getExchange() {
        return exchange;
    }

    public void setExchange(BigDecimal exchange) {
        this.exchange = exchange;
    }
}
