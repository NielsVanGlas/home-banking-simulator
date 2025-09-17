package com.niels.homebanking.repository;

import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.id = ?1 AND t.account.user.id = ?2")
    Optional<Transaction> findByIdAndUserId(UUID id, UUID authenticatedUser);

    @Query(value = "SELECT new com.niels.homebanking.dto.transaction.ShowTransactionDto(t.id, t.account, t.cause, t.dateTime, t.status, t.value, t.waiting) FROM Transaction t WHERE t.account.user.id = ?1")
    Page<ShowTransactionDto> findByUserId(UUID authenticatedUser, Pageable pagination);

}
