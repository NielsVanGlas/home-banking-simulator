package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.dto.transaction.UpdateTransactionDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.Transaction;
import com.niels.homebanking.entity.TransactionStatus;
import com.niels.homebanking.factory.TransactionFactory;
import com.niels.homebanking.repository.BankAccountRepository;
import com.niels.homebanking.repository.TransactionRepository;
import com.niels.homebanking.repository.TransactionStatusRepository;
import com.niels.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.niels.homebanking.util.Constant.ERR_0005;
import static com.niels.homebanking.util.Constant.ERR_0009;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public UUID createTransaction(CreateTransactionDto createTransactionDto, UUID authenticatedUser) throws BaseException {
        TransactionStatus transactionStatus = transactionStatusRepository.findById(createTransactionDto.getStatus()).orElseThrow(() -> new BaseException(ERR_0005, HttpStatus.NOT_FOUND));
        BankAccount bankAccount = bankAccountRepository.findByUserId(authenticatedUser).orElseThrow(() -> new BaseException(ERR_0009, HttpStatus.NOT_FOUND));
        return transactionRepository.saveAndFlush(TransactionFactory.createTransaction(createTransactionDto, bankAccount, transactionStatus)).getId();
    }

    @Override
    public Page<ShowTransactionDto> getTransactions(Pageable pagination, UUID authenticatedUser) {
        return transactionRepository.findByUserId(authenticatedUser, pagination);
    }

    @Override
    public void updateTransaction(UUID id, UpdateTransactionDto updateTransactionDto, UUID authenticatedUser) throws BaseException {
        TransactionStatus transactionStatus = transactionStatusRepository.findById(updateTransactionDto.getStatus()).orElseThrow(() -> new BaseException(ERR_0005, HttpStatus.NOT_FOUND));
        BankAccount bankAccount = bankAccountRepository.findByUserId(authenticatedUser).orElseThrow(() -> new BaseException(ERR_0009, HttpStatus.NOT_FOUND));
        Transaction transaction = transactionRepository.findByIdAndUserId(id,authenticatedUser).orElseThrow(() -> new BaseException(ERR_0009, HttpStatus.NOT_FOUND));
        transactionRepository.saveAndFlush(TransactionFactory.updateTransaction(updateTransactionDto, bankAccount, transactionStatus, transaction));
    }

}
