package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSNCBVerification implements Serializable {

	private static final long serialVersionUID = 746542042233694545L;

	private String No;
	private String CustomerFullName;
	private String RegistrationId;
	private String ConsentNo;

	private String TranNo;
	private int RequestObjective;
	private String IdType;
	private String IdNo;
	private String RegistrationType;
	private String CompanyName;
	private String RegistrationDate;
	private String DateOfIssue;
	private String DateOfBirth;
	private String DateOfCardExpiry;
	private String DateOfConsent;
	private String RequestType;
	private String Department;
	
	private String Prefix;
	private String FirstName;
	private String FamilyName;
		
	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	public String getCustomerFullName() {
		return CustomerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		CustomerFullName = customerFullName;
	}

	public String getRegistrationId() {
		return RegistrationId;
	}

	public void setRegistrationId(String registrationId) {
		RegistrationId = registrationId;
	}

	public String getConsentNo() {
		return ConsentNo;
	}

	public void setConsentNo(String consentNo) {
		ConsentNo = consentNo;
	}

	public String getTranNo() {
		return TranNo;
	}

	public void setTranNo(String tranNo) {
		TranNo = tranNo;
	}

	public int getRequestObjective() {
		return RequestObjective;
	}

	public void setRequestObjective(int requestObjective) {
		RequestObjective = requestObjective;
	}

	public String getIdType() {
		return IdType;
	}

	public void setIdType(String idType) {
		IdType = idType;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getRegistrationType() {
		return RegistrationType;
	}

	public void setRegistrationType(String registrationType) {
		RegistrationType = registrationType;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getRegistrationDate() {
		return RegistrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		RegistrationDate = registrationDate;
	}

	public String getDateOfIssue() {
		return DateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		DateOfIssue = dateOfIssue;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getDateOfCardExpiry() {
		return DateOfCardExpiry;
	}

	public void setDateOfCardExpiry(String dateOfCardExpiry) {
		DateOfCardExpiry = dateOfCardExpiry;
	}

	public String getDateOfConsent() {
		return DateOfConsent;
	}

	public void setDateOfConsent(String dateOfConsent) {
		DateOfConsent = dateOfConsent;
	}

	public String getRequestType() {
		return RequestType;
	}

	public void setRequestType(String requestType) {
		RequestType = requestType;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}
	
	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getFamilyName() {
		return FamilyName;
	}

	public void setFamilyName(String familyName) {
		FamilyName = familyName;
	}
}
