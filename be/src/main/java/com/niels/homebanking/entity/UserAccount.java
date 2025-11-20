package com.niels.homebanking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.homebanking.entity.extra.CommonEntity;
import com.niels.homebanking.enumerate.DocumentType;
import com.niels.homebanking.enumerate.Gender;
import com.niels.homebanking.enumerate.Role;
import com.niels.homebanking.enumerate.converter.DocumentTypeConverter;
import com.niels.homebanking.enumerate.converter.GenderConverter;
import com.niels.homebanking.enumerate.converter.RoleConverter;
import com.niels.homebanking.util.Encryptor;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class UserAccount extends CommonEntity implements UserDetails {
    // Account info
    @Column
    private boolean enabled;

    @Column
    private String password;

    @Column
    @Convert(converter = RoleConverter.class)
    private Role role;

    // User info
    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String firstName;

    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String lastName;

    @Column
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column
    private LocalDate bornDate;

    @Column
    @Convert(converter = Encryptor.class)
    private String birthCity;

    @Column
    @Convert(converter = Encryptor.class)
    private String birthProvinceCode;

    @Column
    private String birthZipCode;

    @Column(nullable = false, unique = true)
    @Convert(converter = Encryptor.class)
    private String taxCode;

    // Contacts
    @Column(nullable = false, unique = true)
    @Convert(converter = Encryptor.class)
    private String email;

    @Column
    @Convert(converter = Encryptor.class)
    private String mobile;

    // Residence
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "residence_id", nullable = false)
    @JsonBackReference
    private Address residence;

    // Domicile
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "home_id", nullable = false)
    @JsonBackReference
    private Address home;

    // Terms and Conditions
    @Column(nullable = false)
    private boolean marketingConsensus;

    @Column(nullable = false)
    private boolean serviceTermsAndConditions;

    // ID Card or Passport
    @Column(nullable = false)
    @Convert(converter = DocumentTypeConverter.class)
    private DocumentType documentType;

    @Column(nullable = false)
    @Convert(converter = Encryptor.class)
    private String documentId;

    // Constructors
    public UserAccount() {
    }

    public UserAccount(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, boolean enabled, String password, Role role, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, Address residence, Address home, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        super(id, createdAt, updatedAt);
        this.enabled = enabled;
        this.password = password;
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

    public UserAccount(boolean enabled, String password, Role role, String firstName, String lastName, Gender gender, LocalDate bornDate, String birthCity, String birthProvinceCode, String birthZipCode, String taxCode, String email, String mobile, Address residence, Address home, boolean marketingConsensus, boolean serviceTermsAndConditions, DocumentType documentType, String documentId) {
        this.enabled = enabled;
        this.password = password;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getValue()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Address getResidence() {
        return residence;
    }

    public void setResidence(Address residence) {
        this.residence = residence;
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
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

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
