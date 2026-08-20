package com.niels.homebanking.repository;

import com.niels.homebanking.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, UUID> {

    Optional<TransactionStatus> findByStatus(String status);

}
