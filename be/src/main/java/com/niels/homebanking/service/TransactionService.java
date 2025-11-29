package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.dto.transaction.UpdateTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    UUID createTransaction(CreateTransactionDto createTransactionDto, UUID authenticatedUser) throws ValidationException, BaseException;

    Page<ShowTransactionDto> getTransactions(Pageable pagination, UUID authenticatedUser);

    void updateTransaction(UUID id, UpdateTransactionDto updateTransactionDto, UUID authenticatedUser) throws BaseException;

}
