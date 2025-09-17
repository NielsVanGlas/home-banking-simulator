package com.niels.homebanking.repository;

import com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto;
import com.niels.homebanking.entity.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, UUID> {

    boolean existByStatus(String status);

    @Query(value = "SELECT new com.niels.homebanking.dto.transactionStatus.ShowTransactionStatusDto(ts.id, ts.status) FROM TransactionStatus ts")
    Page<ShowTransactionStatusDto> findAllTransactionStatuses();

}
