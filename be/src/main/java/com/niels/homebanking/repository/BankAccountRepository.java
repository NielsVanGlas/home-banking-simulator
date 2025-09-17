package com.niels.homebanking.repository;

import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.entity.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByIban(String iban);

    Optional<BankAccount> findByName(String name);

    @Query(value = "SELECT ba FROM BankAccount ba WHERE ba.id = ?1 AND ba.user.id = ?2")
    Optional<BankAccount> findByIdAndUserId(UUID id, UUID authenticatedUser);

    @Query(value = "SELECT new com.niels.homebanking.dto.bankAccount.ShowBankAccountDto(ba.id, ba.user, ba.name, ba.iban, ba.currency, ba.balanceDate, ba.balance) FROM BankAccount ba WHERE ba.user.id = ?1")
    Page<ShowBankAccountDto> findByUserId(UUID authenticatedUser, Pageable pageable);

}
