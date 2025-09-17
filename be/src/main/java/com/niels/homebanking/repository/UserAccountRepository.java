package com.niels.homebanking.repository;

import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;
import com.niels.homebanking.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @Query(value = "SELECT new com.niels.homebanking.dto.userAccount.ShowUserAccountDto(ua.id, ua.enabled, ua.password, ua.role, ua.firstName, ua.lastName, ua.gender, ua.bornDate, ua.birthCity, ua.birthProvinceCode, ua.birthZipCode, ua.taxCode, ua.email, ua.mobile, ua.marketingConsensus, ua.serviceTermsAndConditions, ua.documentType, ua.documentId, ua.residence, ua.home) FROM UserAccount ua")
    Page<ShowUserAccountDto> findAllUsers(Pageable pageable);

    Optional<UserAccount> findByTaxCode(String taxCode);

    Optional<UserAccount> findByEmail(String email);
}
