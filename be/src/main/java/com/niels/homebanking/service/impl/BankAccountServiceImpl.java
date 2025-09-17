package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.dto.bankAccount.UpdateBankAccountDto;
import com.niels.homebanking.entity.BankAccount;
import com.niels.homebanking.entity.Currency;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.factory.BankAccountFactory;
import com.niels.homebanking.repository.BankAccountRepository;
import com.niels.homebanking.repository.CurrencyRepository;
import com.niels.homebanking.repository.UserAccountRepository;
import com.niels.homebanking.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.niels.homebanking.util.Constant.*;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public UUID createBankAccount(CreateBankAccountDto createBankAccountDto, UUID authenticatedUser) throws ValidationException {
        UserAccount userAccount = userAccountRepository.findById(authenticatedUser).orElseThrow(() -> new ValidationException(ERR_0006, HttpStatus.BAD_REQUEST));
        Currency currency = currencyRepository.findById(createBankAccountDto.getCurrency()).orElseThrow(() -> new ValidationException(ERR_0007, HttpStatus.BAD_REQUEST));
        if (bankAccountRepository.findByIban(createBankAccountDto.getIban()).isPresent()) {
            throw new ValidationException(ERR_0008, HttpStatus.BAD_REQUEST);
        }
        if (bankAccountRepository.findByName(createBankAccountDto.getName()).isPresent()) {
            throw new ValidationException(ERR_0009, HttpStatus.BAD_REQUEST);
        }
        return bankAccountRepository.saveAndFlush(BankAccountFactory.createUserAccount(createBankAccountDto, userAccount, currency)).getId();
    }

    @Override
    public ShowBankAccountDto getBankAccount(UUID id, UUID authenticatedUser) throws BaseException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByIdAndUserId(id, authenticatedUser);
        if (optionalBankAccount.isPresent()){
            return BankAccountFactory.showBankAccountDto(optionalBankAccount.get());
        }
        throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
    }

    @Override
    public Page<ShowBankAccountDto> getBankAccounts(Pageable pagination, UUID authenticatedUser) {
        return bankAccountRepository.findByUserId(authenticatedUser, pagination);
    }

    @Override
    public void updateBankAccount(UUID id, UUID authenticatedUser, UpdateBankAccountDto updateBankAccountDto) throws ValidationException, BaseException {
        Currency currency = currencyRepository.findById(updateBankAccountDto.getCurrency()).orElseThrow(() -> new ValidationException(ERR_0007, HttpStatus.BAD_REQUEST));
        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(id, authenticatedUser).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
        Optional<BankAccount> bankAccountIban = bankAccountRepository.findByIban(updateBankAccountDto.getIban());
        if (bankAccountIban.isPresent() && !bankAccountIban.get().getId().equals(id)) {
            throw new ValidationException(ERR_0008, HttpStatus.BAD_REQUEST);
        }
        Optional<BankAccount> bankAccountName = bankAccountRepository.findByName(updateBankAccountDto.getName());
        if (bankAccountName.isPresent() && !bankAccountName.get().getId().equals(id)) {
            throw new ValidationException(ERR_0009, HttpStatus.BAD_REQUEST);
        }
        bankAccountRepository.saveAndFlush(BankAccountFactory.updateBankAccount(bankAccount, updateBankAccountDto, currency));
    }

    @Override
    public void deleteBankAccount(UUID id, UUID authenticatedUser) throws BaseException {
        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(id, authenticatedUser).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
        bankAccountRepository.deleteById(bankAccount.getId());
    }
}
