package com.niels.homebanking.factory;

import com.niels.homebanking.dto.userAccount.CreateUserAccountDto;
import com.niels.homebanking.dto.userAccount.ShowUserAccountDto;
import com.niels.homebanking.dto.userAccount.UpdateUserAccountDto;
import com.niels.homebanking.entity.Address;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.enumerate.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAccountFactory {

    public static UserAccount createUserAccount(CreateUserAccountDto dto, Address residence, Address home, PasswordEncoder passwordEncoder) {

        return new UserAccount(
                true,
                passwordEncoder.encode(dto.getPassword()),
                Role.USER,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getBornDate(),
                dto.getBirthCity(),
                dto.getBirthProvinceCode(),
                dto.getBirthZipCode(),
                dto.getTaxCode(),
                dto.getEmail(),
                dto.getMobile(),
                residence,
                home,
                dto.isMarketingConsensus(),
                dto.isServiceTermsAndConditions(),
                dto.getDocumentType(),
                dto.getDocumentId()
        );

    }

    public static ShowUserAccountDto showUserAccountDto(UserAccount entity) {

        return new ShowUserAccountDto(
                entity.isEnabled(),
                entity.getRole(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getBornDate(),
                entity.getBirthCity(),
                entity.getBirthProvinceCode(),
                entity.getBirthZipCode(),
                entity.getTaxCode(),
                entity.getEmail(),
                entity.getMobile(),
                AddressFactory.showAddressDto(entity.getResidence()),
                AddressFactory.showAddressDto(entity.getHome()),
                entity.isMarketingConsensus(),
                entity.isServiceTermsAndConditions(),
                entity.getDocumentType(),
                entity.getDocumentId()
        );

    }

    public static UserAccount updateUserAccount(UserAccount entity, UpdateUserAccountDto dto, Address residence, Address home, PasswordEncoder passwordEncoder) {
        if (dto.getPassword() != null ){
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        entity.setResidence(residence);
        entity.setHome(home);
        entity.setMarketingConsensus(dto.isMarketingConsensus());
        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentId(dto.getDocumentId());
        return entity;
    }
}
