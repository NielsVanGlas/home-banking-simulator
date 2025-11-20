package com.niels.homebanking.dto.userAccount;

import com.niels.homebanking.dto.address.ShowAddressDto;
import com.niels.homebanking.entity.Address;
import com.niels.homebanking.enumerate.DocumentType;
import com.niels.homebanking.enumerate.Gender;
import com.niels.homebanking.enumerate.Role;
import com.niels.homebanking.factory.AddressFactory;

import java.time.LocalDate;
import java.util.UUID;

public class ShowUserAccountDto {

    // Account info
    private UUID id;
    private boolean enabled;
    private Role role;

    // User info
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate bornDate;
    private String birthCity;
    private String birthProvinceCode;
    private String birthZipCode;
    private String taxCode;

    // Contacts
    private String email;
    private String mobile;

    // Residence
    private ShowAddressDto residence;

    // Domicile
    private ShowAddressDto home;

    // Terms and Conditions
    private boolean marketingConsensus;
    private boolean serviceTermsAndConditions;

    // ID Card or Passport
    private DocumentType documentType;
    private String documentId;

    // Constructors
    public ShowUserAccountDto() {
    }

    public ShowUserAccountDto(UUID id, boolean enabled, Role role, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        this.id = id;
        this.enabled = enabled;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.birthCity = birthCity;
        this.birthProvinceCode = birthProvinceCode;
        this.birthZipCode = birthZipCode;
        this.taxCode = taxCode;
        this.email = email;
        this.mobile = mobile;
        this.marketingConsensus = marketingConsensus;
        this.serviceTermsAndConditions = serviceTermsAndConditions;
        this.documentType = documentType;
        this.documentId = documentId;
    }

    public ShowUserAccountDto(UUID id, boolean enabled, Role role, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId, Address residence, Address home) {
        this.id = id;
        this.enabled = enabled;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.birthCity = birthCity;
        this.birthProvinceCode = birthProvinceCode;
        this.birthZipCode = birthZipCode;
        this.taxCode = taxCode;
        this.email = email;
        this.mobile = mobile;
        this.marketingConsensus = marketingConsensus;
        this.serviceTermsAndConditions = serviceTermsAndConditions;
        this.documentType = documentType;
        this.documentId = documentId;
        this.residence = AddressFactory.showAddressDto(residence);
        this.home = AddressFactory.showAddressDto(home);
    }

    public ShowUserAccountDto(UUID id, boolean enabled, Role role, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, ShowAddressDto residence, ShowAddressDto home, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        this.id = id;
        this.enabled = enabled;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.bornDate = bornDate;
        this.birthCity = birthCity;
        this.birthProvinceCode = birthProvinceCode;
        this.birthZipCode = birthZipCode;
        this.taxCode = taxCode;
        this.email = email;
        this.mobile = mobile;
        this.residence = residence;
        this.home = home;
        this.marketingConsensus = marketingConsensus;
        this.serviceTermsAndConditions = serviceTermsAndConditions;
        this.documentType = documentType;
        this.documentId = documentId;
    }

    // Getters and Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthProvinceCode() {
        return birthProvinceCode;
    }

    public void setBirthProvinceCode(String birthProvinceCode) {
        this.birthProvinceCode = birthProvinceCode;
    }

    public String getBirthZipCode() {
        return birthZipCode;
    }

    public void setBirthZipCode(String birthZipCode) {
        this.birthZipCode = birthZipCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ShowAddressDto getResidence() {
        return residence;
    }

    public void setResidence(ShowAddressDto residence) {
        this.residence = residence;
    }

    public ShowAddressDto getHome() {
        return home;
    }

    public void setHome(ShowAddressDto home) {
        this.home = home;
    }

    public boolean isMarketingConsensus() {
        return marketingConsensus;
    }

    public void setMarketingConsensus(boolean marketingConsensus) {
        this.marketingConsensus = marketingConsensus;
    }

    public boolean isServiceTermsAndConditions() {
        return serviceTermsAndConditions;
    }

    public void setServiceTermsAndConditions(boolean serviceTermsAndConditions) {
        this.serviceTermsAndConditions = serviceTermsAndConditions;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
