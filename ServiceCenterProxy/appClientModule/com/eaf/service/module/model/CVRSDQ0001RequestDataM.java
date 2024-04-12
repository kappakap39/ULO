package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class CVRSDQ0001RequestDataM implements Serializable,Cloneable{
	 private ArrayList<CVRSDQ0001AddressDataM> otherAddresses;
	 private String thaiFirstName;
	 private String thaiTitleName;
	 private String thaiLastName;
	 private String thaiMiddleName;
	 private String engFirstName;
	 private String engTitleName;
	 private String engLastName;
	 private String engMiddleName;
	 private String gender;
	 private String marriageStatus;
	 private String customerType;
	 private String customerTypeCode;
	 private String documentType;
	 private Date dateOfBirth;
	 private String idNo;
	 private String issueBy;
	 private Date issueDate;
	 private Date expireDate;
	 private String nationality;
	 private String race;
	 private String occupation;
	 private String profession;
	 private String asset;
	 private String position;
	 private String education;
	 private String salary;
	 private String noOfEmployee;
	 private String consentFlag;
	 private String personalId;
	 private String  businessCode;
	 private String email;
	 private String professionOther;
	 private  CVRSDQ0001AddressDataM contactAddress;
	 private  CVRSDQ0001AddressDataM registrationAddress;
	 private  CVRSDQ0001AddressDataM workAddress;
	 private ArrayList<CVRSDQ0001ContactDataM> contacts;
	 private String businessType;
	 
	public String getThaiFirstName() {
		return thaiFirstName;
	}

	public void setThaiFirstName(String thaiFirstName) {
		this.thaiFirstName = thaiFirstName;
	}

	public String getThaiTitleName() {
		return thaiTitleName;
	}

	public void setThaiTitleName(String thaiTitleName) {
		this.thaiTitleName = thaiTitleName;
	}

	public String getThaiLastName() {
		return thaiLastName;
	}

	public void setThaiLastName(String thaiLastName) {
		this.thaiLastName = thaiLastName;
	}

	public String getThaiMiddleName() {
		return thaiMiddleName;
	}

	public void setThaiMiddleName(String thaiMiddleName) {
		this.thaiMiddleName = thaiMiddleName;
	}

	public String getEngFirstName() {
		return engFirstName;
	}

	public void setEngFirstName(String engFirstName) {
		this.engFirstName = engFirstName;
	}

	public String getEngTitleName() {
		return engTitleName;
	}

	public void setEngTitleName(String engTitleName) {
		this.engTitleName = engTitleName;
	}

	public String getEngLastName() {
		return engLastName;
	}

	public void setEngLastName(String engLastName) {
		this.engLastName = engLastName;
	}

	public String getEngMiddleName() {
		return engMiddleName;
	}

	public void setEngMiddleName(String engMiddleName) {
		this.engMiddleName = engMiddleName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIssueBy() {
		return issueBy;
	}

	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getNoOfEmployee() {
		return noOfEmployee;
	}

	public void setNoOfEmployee(String noOfEmployee) {
		this.noOfEmployee = noOfEmployee;
	}

	public String getConsentFlag() {
		return consentFlag;
	}

	public void setConsentFlag(String consentFlag) {
		this.consentFlag = consentFlag;
	}

	public ArrayList<CVRSDQ0001AddressDataM> getOtherAddresses() {
		return otherAddresses;
	}

	public void setOtherAddresses(
			ArrayList<CVRSDQ0001AddressDataM> otherAddresses) {
		this.otherAddresses = otherAddresses;
	}

	public CVRSDQ0001AddressDataM getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(CVRSDQ0001AddressDataM contactAddress) {
		this.contactAddress = contactAddress;
	}

	public CVRSDQ0001AddressDataM getRegistrationAddress() {
		return registrationAddress;
	}

	public void setRegistrationAddress(
			CVRSDQ0001AddressDataM registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public CVRSDQ0001AddressDataM getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(CVRSDQ0001AddressDataM workAddress) {
		this.workAddress = workAddress;
	}

	public ArrayList<CVRSDQ0001ContactDataM> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<CVRSDQ0001ContactDataM> contacts) {
		this.contacts = contacts;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfessionOther() {
		return professionOther;
	}

	public void setProfessionOther(String professionOther) {
		this.professionOther = professionOther;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}   
	
}
