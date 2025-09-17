package com.niels.homebanking.factory;

import com.niels.homebanking.dto.currency.CreateCurrencyDto;
import com.niels.homebanking.dto.currency.ShowCurrencyDto;
import com.niels.homebanking.dto.currency.UpdateCurrencyDto;
import com.niels.homebanking.entity.Currency;

public class CurrencyFactory {

    public static ShowCurrencyDto showCurrencyDto(Currency entity) {
        return new ShowCurrencyDto(
                entity.getId(),
                entity.getName(),
                entity.getIso(),
                entity.getSymbol(),
                entity.getExchange()
        );
    }

    public static Currency createCurrency(CreateCurrencyDto dto) {
        return new Currency(
                dto.getName(),
                dto.getIso(),
                dto.getSymbol(),
                dto.getExchange()
        );
    }

    public static Currency updateCurrency(UpdateCurrencyDto dto, Currency entity) {
        entity.setName(dto.getName());
        entity.setIso(dto.getIso());
        entity.setSymbol(dto.getSymbol());
        entity.setExchange(dto.getExchange());
        return entity;
    }
}
