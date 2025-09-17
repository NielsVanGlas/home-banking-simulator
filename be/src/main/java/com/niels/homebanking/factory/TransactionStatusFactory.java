package com.niels.homebanking.factory;

import com.niels.homebanking.dto.transactionStatus.CreateTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.UpdateTransactionStatusDto;
import com.niels.homebanking.entity.TransactionStatus;

public class TransactionStatusFactory {
    public static TransactionStatus createTransactionStatus(CreateTransactionStatusDto dto) {
        return new TransactionStatus(
                dto.getStatus()
        );
    }

    public static ShowTransactionStatusDto showTransactionStatusDto(TransactionStatus entity) {
        return new ShowTransactionStatusDto(
                entity.getId(),
                entity.getStatus()
        );
    }

    public static TransactionStatus updateTransactionStatus(UpdateTransactionStatusDto dto, TransactionStatus entity) {
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
