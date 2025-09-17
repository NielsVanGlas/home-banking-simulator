package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.transactionStatus.CreateTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.UpdateTransactionStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionStatusService {
    UUID createTransactionStatus(CreateTransactionStatusDto createTransactionStatusDto) throws ValidationException;

    ShowTransactionStatusDto getTransactionStatus(UUID id) throws BaseException;

    Page<ShowTransactionStatusDto> getTransactionStatuss(Pageable pagination);

    void updateTransactionStatus(UUID id, UpdateTransactionStatusDto updateTransactionStatusDto) throws ValidationException, BaseException;

    void deleteTransactionStatus(UUID id) throws BaseException;
}
