package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.dto.transactionStatus.CreateTransactionStatusDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.TransactionStatus;
import com.niels.homebanking.factory.BankAccountFactory;
import com.niels.homebanking.factory.TransactionFactory;
import com.niels.homebanking.factory.TransactionStatusFactory;
import com.niels.homebanking.repository.BankAccountRepository;
import com.niels.homebanking.repository.TransactionRepository;
import com.niels.homebanking.repository.TransactionStatusRepository;
import com.niels.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.niels.homebanking.util.Constant.ERR_0004;
import static com.niels.homebanking.util.Constant.ERR_0007;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;



    @Override
    public UUID createTransaction(CreateTransactionDto createTransactionDto, UUID authenticatedUser) throws BaseException, ValidationException {
        if (createTransactionDto.getStatus() == null) {
            createTransactionDto.setStatus("PROCESSING");
        }
        TransactionStatus transactionStatus = transactionStatusRepository.findByStatus(createTransactionDto.getStatus()).orElseGet(() -> transactionStatusRepository.saveAndFlush(TransactionStatusFactory.createTransactionStatus(new CreateTransactionStatusDto(createTransactionDto.getStatus()))));
        BankAccount bankAccount = bankAccountRepository.findByUserId(authenticatedUser).orElseThrow(() -> new BaseException(ERR_0007, HttpStatus.NOT_FOUND));
        BigDecimal newBalance = bankAccount.getBalance().add(createTransactionDto.getValue());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(ERR_0004, HttpStatus.BAD_REQUEST);
        }
        bankAccountRepository.saveAndFlush(BankAccountFactory.updateBankAccount(bankAccount, newBalance));
        return transactionRepository.saveAndFlush(TransactionFactory.createTransaction(createTransactionDto, bankAccount, transactionStatus)).getId();
    }

    @Override
    public Page<ShowTransactionDto> getTransactions(Pageable pagination, UUID authenticatedUser) {
        return transactionRepository.findByUserId(authenticatedUser, pagination);
    }

}
