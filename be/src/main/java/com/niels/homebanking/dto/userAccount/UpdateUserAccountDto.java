package com.niels.homebanking.dto.userAccount;

import com.niels.homebanking.dto.address.UpdateAddressDto;
import com.niels.homebanking.enumerate.DocumentType;

public class UpdateUserAccountDto {

    // Account info
    private String password;

    // Contacts
    private String email;
    private String mobile;

    // Residence
    private UpdateAddressDto residence;

    // Domicile
    private UpdateAddressDto home;

    // Terms and Conditions
    private boolean marketingConsensus;

    // ID Card or Passport
    private DocumentType documentType;
    private String documentId;

    // Constructors
    public UpdateUserAccountDto() {
    }

    public UpdateUserAccountDto(String password, String email, String mobile, boolean marketingConsensus, DocumentType documentType, String documentId) {
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.marketingConsensus = marketingConsensus;
        this.documentType = documentType;
        this.documentId = documentId;
    }

    public UpdateUserAccountDto(String password, String email, String mobile, UpdateAddressDto residence, UpdateAddressDto home, boolean marketingConsensus, DocumentType documentType, String documentId) {
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.residence = residence;
        this.home = home;
        this.marketingConsensus = marketingConsensus;
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

    public UpdateAddressDto getResidence() {
        return residence;
    }

    public void setResidence(UpdateAddressDto residence) {
        this.residence = residence;
    }

    public UpdateAddressDto getHome() {
        return home;
    }

    public void setHome(UpdateAddressDto home) {
        this.home = home;
    }

    public boolean isMarketingConsensus() {
        return marketingConsensus;
    }

    public void setMarketingConsensus(boolean marketingConsensus) {
        this.marketingConsensus = marketingConsensus;
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
