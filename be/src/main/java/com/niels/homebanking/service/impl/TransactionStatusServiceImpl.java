package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.transactionStatus.CreateTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;
import com.niels.homebanking.dto.transactionStatus.UpdateTransactionStatusDto;
import com.niels.homebanking.entity.TransactionStatus;
import com.niels.homebanking.factory.TransactionStatusFactory;
import com.niels.homebanking.repository.TransactionStatusRepository;
import com.niels.homebanking.service.TransactionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.niels.homebanking.util.Constant.ERR_0003;
import static com.niels.homebanking.util.Constant.ERR_0004;

@Service
public class TransactionStatusServiceImpl implements TransactionStatusService {

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Override
    public UUID createTransactionStatus(CreateTransactionStatusDto createTransactionStatusDto) throws ValidationException {

        if (transactionStatusRepository.findByStatus(createTransactionStatusDto.getStatus()).isEmpty()) {
            throw new ValidationException(ERR_0004, HttpStatus.BAD_REQUEST);
        }
        TransactionStatus transactionStatus = transactionStatusRepository.saveAndFlush(TransactionStatusFactory.createTransactionStatus(createTransactionStatusDto));
        return transactionStatus.getId();
    }

    @Override
    public ShowTransactionStatusDto getTransactionStatus(UUID id) throws BaseException {
        Optional<TransactionStatus> optionalTransactionStatus = transactionStatusRepository.findById(id);
        if (optionalTransactionStatus.isPresent()) {
            return TransactionStatusFactory.showTransactionStatusDto(optionalTransactionStatus.get());
        } else {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Page<ShowTransactionStatusDto> getTransactionStatuss(Pageable pagination) {
        return transactionStatusRepository.findAllTransactionStatuses(pagination);
    }

    @Override
    public void updateTransactionStatus(UUID id, UpdateTransactionStatusDto updateTransactionStatusDto) throws ValidationException, BaseException {
        if (transactionStatusRepository.findByStatus(updateTransactionStatusDto.getStatus()).isEmpty()) {
            throw new ValidationException(ERR_0004, HttpStatus.BAD_REQUEST);
        }
        TransactionStatus transactionStatus = transactionStatusRepository.findById(id).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
        transactionStatusRepository.saveAndFlush(TransactionStatusFactory.updateTransactionStatus(updateTransactionStatusDto, transactionStatus));
    }

    @Override
    public void deleteTransactionStatus(UUID id) throws BaseException {
        Optional<TransactionStatus> optionalTransactionStatus = transactionStatusRepository.findById(id);
        if (optionalTransactionStatus.isPresent()) {
            transactionStatusRepository.deleteById(optionalTransactionStatus.get().getId());
        } else {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
    }
}
