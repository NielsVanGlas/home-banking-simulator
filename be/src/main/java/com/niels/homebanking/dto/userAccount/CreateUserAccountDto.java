package com.niels.homebanking.dto.userAccount;

import com.niels.homebanking.dto.address.CreateAddressDto;
import com.niels.homebanking.enumerate.DocumentType;
import com.niels.homebanking.enumerate.Gender;

import java.time.LocalDate;

public class CreateUserAccountDto {

    // Account info
    private String password;

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
    private CreateAddressDto residence;

    // Domicile
    private CreateAddressDto home;

    // Terms and Conditions
    private boolean marketingConsensus;
    private boolean serviceTermsAndConditions;

    // ID Card or Passport
    private DocumentType documentType;
    private String documentId;

    // Constructors
    public CreateUserAccountDto() {
    }

    public CreateUserAccountDto(String password, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        this.password = password;
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

    public CreateUserAccountDto(String password, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, CreateAddressDto residence, CreateAddressDto home, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        this.password = password;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public CreateAddressDto getResidence() {
        return residence;
    }

    public void setResidence(CreateAddressDto residence) {
        this.residence = residence;
    }

    public CreateAddressDto getHome() {
        return home;
    }

    public void setHome(CreateAddressDto home) {
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
