package com.niels.homebanking.service.impl;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.userAccount.CreateUserAccountDto;
import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;
import com.niels.homebanking.dto.userAccount.UpdateUserAccountDto;
import com.niels.homebanking.entity.Address;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.factory.UserAccountFactory;
import com.niels.homebanking.repository.UserAccountRepository;
import com.niels.homebanking.service.AddressService;
import com.niels.homebanking.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.niels.homebanking.util.Constant.*;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UUID createUserAccount(CreateUserAccountDto createUserAccountDto) throws ValidationException {

        if (userAccountRepository.findByTaxCode(createUserAccountDto.getTaxCode()).isPresent()) {
            throw new ValidationException(ERR_0001, HttpStatus.BAD_REQUEST);
        }

        if (userAccountRepository.findByEmail(createUserAccountDto.getEmail()).isPresent()) {
            throw new ValidationException(ERR_0002, HttpStatus.BAD_REQUEST);
        }

        Address residence = addressService.getOrCreateAddress(createUserAccountDto.getResidence());

        Address home = createUserAccountDto.getHome() != null
                ? addressService.getOrCreateAddress(createUserAccountDto.getHome())
                : residence;

        UserAccount userAccount = userAccountRepository.saveAndFlush(UserAccountFactory.createUserAccount(createUserAccountDto, residence, home, passwordEncoder));
        return userAccount.getId();

    }

    @Override
    public ShowUserAccountDto getUserAccount(UUID authenticatedUser) throws BaseException {
        return UserAccountFactory.showUserAccountDto(userAccountRepository.findById(authenticatedUser).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND)));
    }

    @Override
    public Page<ShowUserAccountDto> getUserAccounts(Pageable pageable) {
        return userAccountRepository.findAllUsers(pageable);
    }

    @Override
    public void updateUserAccount(UUID id, UUID authenticatedUser, UpdateUserAccountDto updateUserAccountDto) throws BaseException, ValidationException {
        if (id.equals(authenticatedUser)) {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(id);
        if (optionalUserAccount.isEmpty()) {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
        Optional<UserAccount> userAccountTaxCode = userAccountRepository.findByTaxCode(updateUserAccountDto.getTaxCode());
        if (userAccountTaxCode.isPresent() && !userAccountTaxCode.get().getId().equals(id)) {
            throw new ValidationException(ERR_0001, HttpStatus.BAD_REQUEST);
        }
        Optional<UserAccount> userAccountEmail = userAccountRepository.findByEmail(updateUserAccountDto.getEmail());
        if (userAccountEmail.isPresent() && !userAccountEmail.get().getId().equals(id)) {
            throw new ValidationException(ERR_0002, HttpStatus.BAD_REQUEST);
        }
        Address residence = addressService.getOrUpdateAddress(updateUserAccountDto.getResidence());
        Address home = updateUserAccountDto.getHome() != null
                ? addressService.getOrUpdateAddress(updateUserAccountDto.getHome())
                : residence;
        userAccountRepository.saveAndFlush(UserAccountFactory.updateUserAccount(optionalUserAccount.get(), updateUserAccountDto, residence, home, passwordEncoder));
    }

    @Override
    public void deleteUserAccount(UUID id, UUID authenticatedUser) throws BaseException {
        if (id.equals(authenticatedUser)) {
            UserAccount userAccount = userAccountRepository.findById(id).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
            userAccountRepository.deleteById(userAccount.getId());
        } else {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updateEmail(UUID id, UUID authenticatedUser, String email) throws BaseException {
        if (id.equals(authenticatedUser)) {
            UserAccount userAccount = userAccountRepository.findById(id).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
            userAccount.setEmail(email);
            userAccountRepository.saveAndFlush(userAccount);
        } else {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updatePassword(UUID id, UUID authenticatedUser, String password) throws BaseException {
        if (id.equals(authenticatedUser)) {
            UserAccount userAccount = userAccountRepository.findById(id).orElseThrow(() -> new BaseException(ERR_0003, HttpStatus.NOT_FOUND));
            userAccount.setPassword(passwordEncoder.encode(password));
            userAccountRepository.saveAndFlush(userAccount);
        } else {
            throw new BaseException(ERR_0003, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername (String name) throws UsernameNotFoundException {
        Optional<UserAccount> optionalUserAccount = UUID_REGEX.matcher(name).matches()
                ? userAccountRepository.findById(UUID.fromString(name))
                : userAccountRepository.findByEmail(name);
        return optionalUserAccount.orElseThrow(() -> new UsernameNotFoundException(name + ": User not found"));
    }

    @Override
    public void enableAccount(UserAccount userAccount) {
        userAccount.setEnabled(true);
        userAccountRepository.saveAndFlush(userAccount);
    }

}
