package com.niels.homebanking.factory;

import com.niels.homebanking.dto.transactionStatus.CreateTransactionStatusDto;
import com.niels.homebanking.entity.TransactionStatus;

public class TransactionStatusFactory {
    public static TransactionStatus createTransactionStatus(CreateTransactionStatusDto dto) {
        return new TransactionStatus(
                dto.getStatus()
        );
    }

}
