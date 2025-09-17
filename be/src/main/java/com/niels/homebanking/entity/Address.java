package com.niels.homebanking.entity;

import com.niels.homebanking.entity.extra.CommonEntity;
import com.niels.homebanking.util.Encryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Address extends CommonEntity {

    @Column
    @Convert(converter = Encryptor.class)
    private String address;

    @Column
    @Convert(converter = Encryptor.class)
    private String city;

    @Column
    @Convert(converter = Encryptor.class)
    private String zipCode;

    @Column
    @Convert(converter = Encryptor.class)
    private String provinceCode;

    @Column
    @Convert(converter = Encryptor.class)
    private String countryCode;

    public Address() {
    }

    public Address(String address, String city, String zipCode, String provinceCode, String countryCode) {
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.provinceCode = provinceCode;
        this.countryCode = countryCode;
    }

    public Address(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String address, String city, String zipCode, String provinceCode, String countryCode) {
        super(id, createdAt, updatedAt);
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.provinceCode = provinceCode;
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
