package com.niels.homebanking.repository;

import com.niels.homebanking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByIban(String iban);

    Optional<BankAccount> findByName(String name);

    @Query(value = "SELECT ba FROM BankAccount ba WHERE ba.user.id = ?1")
    Optional<BankAccount> findByUserId(UUID authenticatedUser);

}
