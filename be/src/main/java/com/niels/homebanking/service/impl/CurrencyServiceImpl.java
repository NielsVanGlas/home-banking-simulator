package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.currency.CreateCurrencyDto;
import com.niels.homebanking.dto.currency.ShowCurrencyDto;
import com.niels.homebanking.dto.currency.UpdateCurrencyDto;
import com.niels.homebanking.entity.Currency;
import com.niels.homebanking.factory.CurrencyFactory;
import com.niels.homebanking.repository.CurrencyRepository;
import com.niels.homebanking.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.niels.homebanking.util.Constant.ERR_0007;
import static com.niels.homebanking.util.Constant.ERR_0010;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public UUID createCurrency(CreateCurrencyDto createCurrencyDto) throws ValidationException {
        if (currencyRepository.findByIso(createCurrencyDto.getIso()).isPresent()) {
            throw new ValidationException(ERR_0010, HttpStatus.BAD_REQUEST);
        }
        return currencyRepository.saveAndFlush(CurrencyFactory.createCurrency(createCurrencyDto)).getId();
    }

    @Override
    public Page<ShowCurrencyDto> getCurrencies(Pageable pagination) {
        return currencyRepository.findCurrencies(pagination);
    }

    @Override
    public void updateCurrency(UUID id, UpdateCurrencyDto updateCurrencyDto) throws BaseException {
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new BaseException(ERR_0007, HttpStatus.NOT_FOUND));
        currencyRepository.saveAndFlush(CurrencyFactory.updateCurrency(updateCurrencyDto, currency));
    }

}
