package com.niels.homebanking.service;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.userAccount.CreateUserAccountDto;
import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;
import com.niels.homebanking.dto.userAccount.UpdateUserAccountDto;
import com.niels.homebanking.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountService extends UserDetailsService {

    UUID createUserAccount(CreateUserAccountDto createUserAccountDto) throws ValidationException;

    ShowUserAccountDto getUserAccount(UUID authenticatedUser) throws BaseException;

    Page<ShowUserAccountDto> getUserAccounts(Pageable pageable);

    void updateUserAccount(UUID id, UUID authenticatedUser, UpdateUserAccountDto updateUserAccountDto) throws BaseException, ValidationException;

    void deleteUserAccount(UUID id, UUID authenticatedUser) throws BaseException;

    void updateEmail(UUID id, UUID authenticatedUser, String email) throws BaseException;

    void updatePassword(UUID id, UUID authenticatedUser, String password) throws BaseException;

    Optional<UserAccount> findById(UUID id);

    void enableAccount(UserAccount userAccount);
}
