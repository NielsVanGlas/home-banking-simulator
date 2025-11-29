package com.niels.homebanking.factory;

import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.dto.transaction.UpdateTransactionDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.Transaction;
import com.niels.homebanking.entity.TransactionStatus;

public class TransactionFactory {

    public static ShowTransactionDto showTransactionDto(Transaction entity) {
        return new ShowTransactionDto(
                entity.getId(),
                entity.getCause(),
                entity.getDateTime(),
                TransactionStatusFactory.showTransactionStatusDto(entity.getStatus()),
                entity.getValue(),
                entity.isWaiting()
        );
    }

    public static Transaction createTransaction(CreateTransactionDto dto, BankAccount account, TransactionStatus status) {
        return new Transaction(
                account,
                dto.getCause(),
                dto.getDateTime(),
                status,
                dto.getValue(),
                dto.isWaiting()
        );
    }

    public static Transaction updateTransaction(UpdateTransactionDto dto, BankAccount account, TransactionStatus status, Transaction entity) {
        entity.setAccount(account);
        entity.setCause(dto.getCause());
        entity.setDateTime(dto.getDateTime());
        entity.setStatus(status);
        entity.setValue(dto.getValue());
        entity.setWaiting(dto.isWaiting());
        return entity;
    }
}
