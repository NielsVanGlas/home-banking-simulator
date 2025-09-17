package com.niels.homebanking.factory;

import com.niels.homebanking.dto.address.CreateAddressDto;
import com.niels.homebanking.dto.address.ShowAddressDto;
import com.niels.homebanking.dto.address.UpdateAddressDto;
import com.niels.homebanking.entity.Address;
import com.niels.homebanking.repository.AddressRepository;

public class AddressFactory {

    public static Address createAddress(CreateAddressDto dto, AddressRepository addressRepository) {
        return addressRepository.saveAndFlush(new Address(
                dto.getAddress(),
                dto.getCity(),
                dto.getZipCode(),
                dto.getProvinceCode(),
                dto.getCountryCode()
        ));
    }

    public static Address updateAddress(UpdateAddressDto dto, AddressRepository addressRepository) {
        return addressRepository.saveAndFlush(new Address(
                dto.getAddress(),
                dto.getCity(),
                dto.getZipCode(),
                dto.getProvinceCode(),
                dto.getCountryCode()
        ));
    }

    public static ShowAddressDto showAddressDto(Address entity) {
        return new ShowAddressDto(
                entity.getId(),
                entity.getAddress(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getProvinceCode(),
                entity.getCountryCode()
        );
    }

}
