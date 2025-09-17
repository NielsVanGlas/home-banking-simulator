package com.niels.homebanking.repository;

import com.niels.homebanking.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query(value = "SELECT a FROM Address a WHERE a.address = ?1 AND a.city = ?2 AND a.zipCode = ?3 AND a.provinceCode = ?4 AND a.countryCode = ?5")
    Optional<Address> findIfExist(String address, String city, String zipCode, String provinceCode, String countryCode);
}
