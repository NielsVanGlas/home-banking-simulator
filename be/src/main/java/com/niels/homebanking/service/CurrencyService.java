package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.currency.CreateCurrencyDto;
import com.niels.homebanking.dto.currency.ShowCurrencyDto;
import com.niels.homebanking.dto.currency.UpdateCurrencyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CurrencyService {
    UUID createCurrency(CreateCurrencyDto createCurrencyDto) throws ValidationException;

    Page<ShowCurrencyDto> getCurrencies(Pageable pagination);

    void updateCurrency(UUID id, UpdateCurrencyDto updateCurrencyDto) throws BaseException;

}
