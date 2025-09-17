package com.niels.homebanking.service.impl;

import com.niels.homebanking.dto.address.CreateAddressDto;
import com.niels.homebanking.dto.address.UpdateAddressDto;
import com.niels.homebanking.entity.Address;
import com.niels.homebanking.factory.AddressFactory;
import com.niels.homebanking.repository.AddressRepository;
import com.niels.homebanking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address getOrCreateAddress(CreateAddressDto dto) {
        Optional<Address> optionalAddress = addressRepository.findIfExist(dto.getAddress(), dto.getCity(), dto.getZipCode(), dto.getProvinceCode(), dto.getCountryCode());
        return optionalAddress.orElseGet(() -> AddressFactory.createAddress(dto, addressRepository));
    }

    @Override
    public Address getOrUpdateAddress(UpdateAddressDto dto) {
        Optional<Address> optionalAddress = addressRepository.findIfExist(dto.getAddress(), dto.getCity(), dto.getZipCode(), dto.getProvinceCode(), dto.getCountryCode());
        return optionalAddress.orElseGet(() -> AddressFactory.updateAddress(dto, addressRepository));
    }

}
