package com.eaf.inf.batch.ulo.kemail;

import java.io.Serializable;

public class KEmailDataM implements Serializable, Cloneable
{
	private String appGroupId;
	private String appRecordId;
	
	private String CustomerName;
	private String DocumentPassword;
	private String Channel;
	private String CisID;
	private String CustomerType;
	private String RegisterBy;
	private String RequestDate;
	private String RequestID;
	
	//Account
	private String AccountNameEnglish;
	private String AccountNameThai;
	private String AccountNumber;
	private String Cycle;
	private String EmailAddress;
	private String Format;
	private String Language;
	private String Product;
	private String SendMethod;
	
	public String getAppGroupId() {
		return appGroupId;
	}
	public void setAppGroupId(String appGroupId) {
		this.appGroupId = appGroupId;
	}
	public String getAppRecordId() {
		return appRecordId;
	}
	public void setAppRecordId(String appRecordId) {
		this.appRecordId = appRecordId;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getDocumentPassword() {
		return DocumentPassword;
	}
	public void setDocumentPassword(String documentPassword) {
		DocumentPassword = documentPassword;
	}
	public String getChannel() {
		return Channel;
	}
	public void setChannel(String channel) {
		Channel = channel;
	}
	public String getCisID() {
		return CisID;
	}
	public void setCisID(String cisID) {
		CisID = cisID;
	}
	public String getCustomerType() {
		return CustomerType;
	}
	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}
	public String getRegisterBy() {
		return RegisterBy;
	}
	public void setRegisterBy(String registerBy) {
		RegisterBy = registerBy;
	}
	public String getRequestDate() {
		return RequestDate;
	}
	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}
	public String getRequestID() {
		return RequestID;
	}
	public void setRequestID(String requestID) {
		RequestID = requestID;
	}
	public String getAccountNameEnglish() {
		return AccountNameEnglish;
	}
	public void setAccountNameEnglish(String accountNameEnglish) {
		AccountNameEnglish = accountNameEnglish;
	}
	public String getAccountNameThai() {
		return AccountNameThai;
	}
	public void setAccountNameThai(String accountNameThai) {
		AccountNameThai = accountNameThai;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String getCycle() {
		return Cycle;
	}
	public void setCycle(String cycle) {
		Cycle = cycle;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}
	public String getSendMethod() {
		return SendMethod;
	}
	public void setSendMethod(String sendMethod) {
		SendMethod = sendMethod;
	}
}
