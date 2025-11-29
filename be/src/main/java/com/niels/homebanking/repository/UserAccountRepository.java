package com.niels.homebanking.repository;

import com.niels.homebanking.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByTaxCode(String taxCode);

    @Query(value = "SELECT ua FROM UserAccount ua WHERE ua.email = ?1")
    Optional<UserAccount> findByEmail(String email);

}
