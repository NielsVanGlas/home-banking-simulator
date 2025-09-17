package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.dto.bankAccount.UpdateBankAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BankAccountService {
    UUID createBankAccount(CreateBankAccountDto createBankAccountDto, UUID authenticatedUser) throws ValidationException;

    ShowBankAccountDto getBankAccount(UUID id, UUID authenticatedUser) throws BaseException;

    Page<ShowBankAccountDto> getBankAccounts(Pageable pagination, UUID authenticatedUser);

    void updateBankAccount(UUID id, UUID authenticatedUser, UpdateBankAccountDto updateBankAccountDto) throws ValidationException, BaseException;

    void deleteBankAccount(UUID id, UUID authenticatedUser) throws BaseException;
}
