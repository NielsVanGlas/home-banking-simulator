package com.niels.homebanking.dto.address;

import java.util.UUID;

public class ShowAddressDto {

    private UUID id;

    private String address;

    private String city;

    private String zipCode;

    private String provinceCode;

    private String countryCode;

    public ShowAddressDto() {
    }

    public ShowAddressDto(UUID id, String address, String city, String zipCode, String provinceCode, String countryCode) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.provinceCode = provinceCode;
        this.countryCode = countryCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
