package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;

import java.util.UUID;

public interface BankAccountService {
    UUID createBankAccount(CreateBankAccountDto createBankAccountDto, UUID authenticatedUser) throws ValidationException, BaseException;

    ShowBankAccountDto getBankAccount(UUID authenticatedUser) throws BaseException;

    void deleteBankAccount(UUID authenticatedUser) throws BaseException;
}
