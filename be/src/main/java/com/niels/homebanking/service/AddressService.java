package com.niels.homebanking.service;

import com.niels.homebanking.dto.address.CreateAddressDto;
import com.niels.homebanking.dto.address.UpdateAddressDto;
import com.niels.homebanking.entity.Address;

public interface AddressService {
    Address getOrCreateAddress(CreateAddressDto residence);

    Address getOrUpdateAddress(UpdateAddressDto dto);
}
