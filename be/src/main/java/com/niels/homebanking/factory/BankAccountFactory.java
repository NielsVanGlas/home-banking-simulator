package com.niels.homebanking.factory;

import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.dto.bankAccount.UpdateBankAccountDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.Currency;
import com.niels.homebanking.entity.UserAccount;

public class BankAccountFactory {

    public static BankAccount createUserAccount(CreateBankAccountDto dto, UserAccount userEntity, Currency currencyEntity) {
        return new BankAccount(
                userEntity,
                dto.getName(),
                dto.getIban(),
                currencyEntity,
                dto.getBalanceDate(),
                dto.getBalance()
        );
    }

    public static ShowBankAccountDto showBankAccountDto(BankAccount entity) {
        return new ShowBankAccountDto(
                entity.getId(),
                UserAccountFactory.showUserAccountDto(entity.getUser()),
                entity.getName(),
                entity.getIban(),
                CurrencyFactory.showCurrencyDto(entity.getCurrency()),
                entity.getBalanceDate(),
                entity.getBalance()
        );
    }

    public static BankAccount updateBankAccount(BankAccount entity, UpdateBankAccountDto dto, Currency currencyEntity) {
        entity.setName(dto.getName());
        entity.setIban(dto.getIban());
        entity.setCurrency(currencyEntity);
        entity.setBalanceDate(dto.getBalanceDate());
        entity.setBalance(dto.getBalance());
        return entity;
    }
}
