package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.dto.bankAccount.UpdateBankAccountDto;

import java.util.UUID;

public interface BankAccountService {
    UUID createBankAccount(CreateBankAccountDto createBankAccountDto, UUID authenticatedUser) throws ValidationException;

    ShowBankAccountDto getBankAccount(UUID authenticatedUser) throws BaseException;

    UUID updateBankAccount(UUID authenticatedUser, UpdateBankAccountDto updateBankAccountDto) throws ValidationException, BaseException;

    void deleteBankAccount(UUID authenticatedUser) throws BaseException;
}
