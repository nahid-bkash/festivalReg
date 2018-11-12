package com.bkash.festivalreg.domain;


import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Registration {
    @Id
  //  @GeneratedValue
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "FOLK_FEST_APP_REGISTRATION_SEQ")
    @SequenceGenerator(name = "FOLK_FEST_APP_REGISTRATION_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;
    private String accountNumber;
    private String accountFirstName;
    private String accountLastName;
    private String accountFatherName;
    private String accountMotherName;
    private String accountHasbandWifeName;
    private String autditUser;
    private Date auditDate;

    @Temporal(TemporalType.DATE)
    private Date accountBirthDate;
    private String presentAddress;
    private String permanentAddress;
    private String gender;
    private String idType;
    private String idNumber;
    private Boolean dataSaved;

    private Long formSerial;

    @Column(name = "estimatedMonthlyIncome",precision=10, scale=2)
    private Double estimatedMonthlyIncome;

    private String sourceOfFund;

    private String detailsOfOccupation;

    @Column(name = "photo_path")
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Long getFormSerial() {
        return formSerial;
    }

    public void setFormSerial(Long formSerial) {
        this.formSerial = formSerial;
    }

    public Boolean getDataSaved() {
        return dataSaved;
    }

    public void setDataSaved(Boolean dataSaved) {
        this.dataSaved = dataSaved;
    }

    public String getAccountHasbandWifeName() {
        return accountHasbandWifeName;
    }

    public void setAccountHasbandWifeName(String accountHasbandWifeName) {
        this.accountHasbandWifeName = accountHasbandWifeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountFirstName() {
        return accountFirstName;
    }

    public void setAccountFirstName(String accountFirstName) {
        this.accountFirstName = accountFirstName;
    }

    public String getAccountLastName() {
        return accountLastName;
    }

    public void setAccountLastName(String accountLastName) {
        this.accountLastName = accountLastName;
    }

    public String getAccountFatherName() {
        return accountFatherName;
    }

    public void setAccountFatherName(String accountFatherName) {
        this.accountFatherName = accountFatherName;
    }

    public String getAccountMotherName() {
        return accountMotherName;
    }

    public void setAccountMotherName(String accountMotherName) {
        this.accountMotherName = accountMotherName;
    }

    public String getAutditUser() {
        return autditUser;
    }

    public void setAutditUser(String autditUser) {
        this.autditUser = autditUser;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getAccountBirthDate() {
        return accountBirthDate;
    }

    public void setAccountBirthDate(Date accountBirthDate) {
        this.accountBirthDate = accountBirthDate;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Double getEstimatedMonthlyIncome() {
        return estimatedMonthlyIncome;
    }

    public void setEstimatedMonthlyIncome(Double estimatedMonthlyIncome) {
        this.estimatedMonthlyIncome = estimatedMonthlyIncome;
    }

    public String getSourceOfFund() {
        return sourceOfFund;
    }

    public void setSourceOfFund(String sourceOfFund) {
        this.sourceOfFund = sourceOfFund;
    }

    public String getDetailsOfOccupation() {
        return detailsOfOccupation;
    }

    public void setDetailsOfOccupation(String detailsOfOccupation) {
        this.detailsOfOccupation = detailsOfOccupation;
    }
}
