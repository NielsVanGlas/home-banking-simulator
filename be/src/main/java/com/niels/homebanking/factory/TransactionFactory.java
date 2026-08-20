package com.niels.homebanking.factory;

import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.Transaction;
import com.niels.homebanking.entity.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionFactory {

    public static Transaction createTransaction(CreateTransactionDto dto, BankAccount account, TransactionStatus status) {
        return new Transaction(
                account,
                dto.getCause(),
                LocalDateTime.now(),
                status,
                dto.getValue()
        );
    }

}
